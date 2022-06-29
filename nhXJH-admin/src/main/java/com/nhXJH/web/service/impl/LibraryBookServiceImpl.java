package com.nhXJH.web.service.impl;

import java.util.*;

import com.nhXJH.common.core.domain.entity.SysDept;
import com.nhXJH.common.core.domain.model.LoginUser;
import com.nhXJH.common.core.page.TableDataInfo;
import com.nhXJH.common.enums.BookTypeEnum;
import com.nhXJH.common.enums.application.TableTypeEnums;
import com.nhXJH.common.exception.ServiceException;
import com.nhXJH.common.utils.DateUtils;
import com.nhXJH.common.utils.StringUtils;
import com.nhXJH.enums.DelEnums;
import com.nhXJH.enums.EnumUtils;
import com.nhXJH.enums.FileTypeEnums;
import com.nhXJH.enums.StatusEnums;
import com.nhXJH.system.domain.po.BaseBookClassCn;
import com.nhXJH.system.mapper.BaseBookClassCnMapper;
import com.nhXJH.system.mapper.SysDeptMapper;
import com.nhXJH.system.service.ISysUserService;
import com.nhXJH.web.domain.*;
import com.nhXJH.web.domain.dto.LibraryBookDto;
import com.nhXJH.web.domain.dto.SearchBookDto;
import com.nhXJH.web.domain.entity.SysFile;
import com.nhXJH.web.domain.param.BookFileParam;
import com.nhXJH.web.domain.param.FileKey;
import com.nhXJH.web.domain.vo.BaseFileClassify;
import com.nhXJH.web.domain.vo.BookVO;
import com.nhXJH.web.domain.vo.LibraryBookDetailVO;
import com.nhXJH.web.mapper.*;
import com.nhXJH.web.service.ILibraryBookService;
import com.nhXJH.web.service.file.AttachFileService;
import com.nhXJH.web.util.collections.OperatorCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * 图书实体信息Service业务层处理
 * 
 * @author xjh
 * @date 2022-02-07
 */
@Service
public class LibraryBookServiceImpl extends ServiceImpl<LibraryBookMapper, LibraryBook>
        implements ILibraryBookService {
    @Autowired
    private LibraryBookMapper libraryBookMapper;
    @Autowired
    private ISysUserService userService;
    @Autowired
    private LibraryPressMapper pressMapper;
    @Autowired
    private LibraryAuthMapper authMapper;
    @Autowired
    private BaseBookClassCnMapper bookTypeMapper;
    @Autowired
    private BaseFileMapper fileMapper;
    @Autowired
    private AttachFileService sysFileService;
    @Autowired
    private SysDeptMapper deptMapper;
    @Autowired
    private OperatorCollection operatorCollection;

    @Value("${local.file.path}")
    private String urlHead;
    /**
     * 查询图书实体信息
     * 
     * @param id 图书实体信息主键
     * @return 图书实体信息
     */
    @Override
    public LibraryBook selectLibraryBookById(Long id) {
        return libraryBookMapper.selectLibraryBookById(id);
    }

    @Override
    public Integer getCount(LibraryBook libraryBook) {
        return libraryBookMapper.getCount(libraryBook);
    }

    /**
     * 查询图书实体信息
     *
     * @param id 图书实体信息主键
     * @return 图书实体信息
     */
    public LibraryBookDetailVO selectLibraryBookDetailById(Long id){
        LibraryBookDetailVO result = new LibraryBookDetailVO();
        if (null == id ){
            return result;
        }
        LibraryBook book = libraryBookMapper.selectLibraryBookById(id);
        if (null == book){
            return result;
        }
        result.setBook(book);//图书信息

        BaseFile query = new BaseFile();
        query.setModelId(book.getId());
        query.setDbId(TableTypeEnums.BOOK.getCode());
        query.setIsDel(DelEnums.NOT_DEL.getValue());
        query.setStatus(StatusEnums.ONE.getDesc());
        List<BaseFile> bookFiles = fileMapper.selectBaseFileList(query);

        BaseFileClassify fileClassify = new BaseFileClassify(bookFiles);
        fileClassify.setUrlHead(this.urlHead);
        result.setBookFile(fileClassify);//图书附件信息

        List<BaseFile> file = fileClassify.getPicFile();
        List<String> src = new ArrayList<>();
        for (BaseFile f : file) {
            src.add(f.getUrl());
        }
        result.setBookFileSrc(src);//图书图片路径列表


        Long press = book.getMechanism();
        if (null != press) {
            LibraryPress p = pressMapper.selectLibraryPressById(press);
            result.setPress(p);//出版社信息
        }


        Long auth = book.getAuthor();
        if(null != auth){
            LibraryAuth a = authMapper.selectLibraryAuthById(auth);
            result.setAuth(a);//作者信息
        }


        Long type = book.getClaz();
        if(null != type){
            BaseBookClassCn t = bookTypeMapper.selectBaseBookClassCnById(type);
            result.setBookType(t);
        }


        return result;
    }
    /**
     * 查询图书实体信息列表
     * 
     * @param libraryBook 图书实体信息
     * @return 图书实体信息
     */
    @Override
    public List<LibraryBook> selectLibraryBookList(LibraryBook libraryBook) {
        return libraryBookMapper.selectLibraryBookList(libraryBook);
    }
    /**
     * 查询图书实体信息列表
     *
     * @param libraryBook 图书实体信息
     * @return 图书实体信息
     */
    @Override
    public List<LibraryBookDto> selectLibraryBookDtoList(LibraryBook libraryBook) {
        List<LibraryBookDto> list = libraryBookMapper.selectLibraryBookDtoList(libraryBook);
        list.forEach( book ->{
            book.setType(BookTypeEnum.getDescByCode(Integer.parseInt(book.getTypeCode())));
        });
        return list;
    }

    public Integer getBookCount(SearchBookDto dto){
        String keyWords = dto.getKeyWords();
        if (null==keyWords || StringUtils.isEmpty(keyWords)){
            return 0;
        }
        List<BookVO> vo = libraryBookMapper.getBookVo(dto);
        Set<Long> set = new HashSet<>();
        int changeSize = 0;
        List<BookVO> result = new ArrayList<>();
        for (BookVO bookVO : vo) {
            set.add(bookVO.getBookId());
            int size = set.size();
            if (changeSize!=size){
                BaseFile query = new BaseFile();
                query.setStatus(StatusEnums.ONE.getDesc());
                query.setDbId(TableTypeEnums.BOOK.getCode());
                query.setModelId(bookVO.getBookId());
                query.setIsDel(DelEnums.NOT_DEL.getValue());
                List<BaseFile> fileList = fileMapper.selectBaseFileList(query);
                bookVO.setFileList(fileList);
                List<String> srcList = new ArrayList<>();
                if (null != fileList) {
                    for (BaseFile baseFile : fileList) {
                        srcList.add(baseFile.getUrl());
                    }
                }
                bookVO.setSrcList(srcList);
                SysDept queryDept = new SysDept();
                if (null != bookVO.getDeptId()) {
                    queryDept.setDeptId(bookVO.getDeptId());
                    bookVO.setParentDept(deptMapper.getParent(queryDept));
                }


                result.add(bookVO);
                changeSize = size;
            }
        }
        return result.size();
    }
    public TableDataInfo getBook(SearchBookDto dto){
        List<BookVO> vo = new ArrayList<>();
        String keyWords = dto.getKeyWords();
        if (null==keyWords || StringUtils.isEmpty(keyWords)){
            return new TableDataInfo(vo,0);
        }

        Integer pageSize = dto.getPageSize();
        if (pageSize<1){
            pageSize = 10;
        }
        Integer pageNum = dto.getPageNum() - 1;
        if (pageNum<0){
            pageNum = 0;
        }
//        int page = pageNum;
        pageNum = pageNum * pageSize ;
        pageSize = pageNum+  pageSize;
        dto.setPageNum(pageNum);
        dto.setPageSize(pageSize);

        vo = libraryBookMapper.getBookVo(dto);
        if (null == vo || vo.size()<1){
            return new TableDataInfo(vo,0);
        }

        Set<Long> set = new HashSet<>();
        int changeSize = 0;
        List<BookVO> result = new ArrayList<>();
        for (BookVO bookVO : vo) {
            set.add(bookVO.getBookId());
            int size = set.size();
            if (changeSize!=size){
                BaseFile query = new BaseFile();
                query.setStatus(StatusEnums.ONE.getDesc());
                query.setDbId(TableTypeEnums.BOOK.getCode());
                query.setModelId(bookVO.getBookId());
                query.setIsDel(DelEnums.NOT_DEL.getValue());
                List<BaseFile> fileList = fileMapper.selectBaseFileList(query);
                bookVO.setFileList(fileList);
                List<String> srcList = new ArrayList<>();
                if (null != fileList) {
                    for (BaseFile baseFile : fileList) {
                        srcList.add(baseFile.getUrl());
                    }
                }
                bookVO.setSrcList(srcList);
                SysDept queryDept = new SysDept();
                if (null != bookVO.getDeptId()) {
                    queryDept.setDeptId(bookVO.getDeptId());
                    bookVO.setParentDept(deptMapper.getParent(queryDept));
                }
                result.add(bookVO);
                changeSize = size;
            }

        }
        return new TableDataInfo(
                result.size()>pageSize?result.subList(pageNum,pageSize):result.subList(pageNum,result.size()),
                result.size());
    }
    /**
     * 新增图书实体信息
     * 
     * @param libraryBook 图书实体信息
     * @return 结果
     */
    @Override
    public int insertLibraryBook(LibraryBook libraryBook) {
        libraryBook.setCreateTime(DateUtils.getNowDate());
        return libraryBookMapper.insertLibraryBook(libraryBook);
    }

    /**
     * 修改图书实体信息
     * 
     * @param libraryBook 图书实体信息
     * @return 结果
     */
    @Override
    public int updateLibraryBook(LibraryBook libraryBook) {
        libraryBook.setUpdateTime(DateUtils.getNowDate());
        return libraryBookMapper.updateLibraryBook(libraryBook);
    }

    /**
     * 批量删除图书实体信息
     * 
     * @param ids 需要删除的图书实体信息主键
     * @return 结果
     */
    @Override
    public int deleteLibraryBookByIds(Long[] ids,Long operator) {
        return libraryBookMapper.deleteLibraryBookByIds(ids,operator);
    }

    /**
     * 删除图书实体信息信息
     * 
     * @param id 图书实体信息主键
     * @return 结果
     */
    @Override
    public int deleteLibraryBookById(Long id,Long operator) {
        return libraryBookMapper.deleteLibraryBookById(id,operator);
    }

    /**
     * 修改图书状态
     *
     * @param book 图书信息
     * @return 结果
     */
    public int updateUserStatus(LibraryBook book){
        return libraryBookMapper.updateLibraryBook(book);
    }

    @Override
    @Transactional
    public BaseFile uploadFile(BookFileParam fileParam) {
        if (null == fileParam){
            throw new ServiceException("无效的文件");
        }
        BaseFile baseFile = new BaseFile();
        baseFile.setFileId(fileParam.getFileId());
        baseFile.setCreateTime(new Date());
        baseFile.setStatus(StatusEnums.ONE.getDesc());
        baseFile.setDbId(TableTypeEnums.BOOK.getCode());
        baseFile.setIsDel(DelEnums.NOT_DEL.getValue());
        baseFile.setModelId(fileParam.getBookId());
        baseFile.setUrl(fileParam.getUrl());
        Integer type = fileParam.getType();
        baseFile.setType(null == type?1:type);
        baseFile.setCreatePerson(fileParam.getUpdatePersonal());
        baseFile.setMark("图书_"+fileParam.getBookTitle());
        List<BaseFile> list = new ArrayList<>();
        list.add(baseFile);
        fileMapper.insertBaseFiles(list);
        return baseFile;
    }

}
