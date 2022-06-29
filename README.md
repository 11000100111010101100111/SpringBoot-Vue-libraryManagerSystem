<div align='center' style='color:#aaa;font-size:42px;font-weight:800;'>一份来自软件工程专业学生的毕业设计案例</div>

#### 简要介绍请看下文...

# SpringBoot-Vue-libraryManagerSystem
  本系统采用 springBoot+vue 前后端分离框架技术，采用市面上应用互联网行业的架构设 计，对于图书管理系统的详细构建。主要是研究对于图书管理行业的业务分析以及当前社会 需求下图书管理系统的价值所在，并且完成在对社会服务上的对接操作。 具体实现功能如下: 用户系统权限控制管理:主要实现用户、管理员、主要领导以及开发者等职能区分，为不 同身份用户提供不同业务功能，疏导业务流向； 用户管理模块:主要实现平台下用户管理，实现用户信息存储与修改、更新与同步、导入 与导出等； 图书管理模块:主要实现图书信息存储与发布、借阅与归还、在线使用图书信息、出入库 等操作，利用互联网技术实现跨越地理限制共享图书信息； 资料管理模块:主要实现图书资料的管理与保存，对不同类型、不同权限、不同级别资料 进行不同程度的保存等； 常用功能模块:常用功能主要面向用户，即图书通过互联网进行图书使用者，这部分人员 将利用此平台查阅书籍文献，对书籍文献进行收藏与摘抄等，次要面向图书管理者，管理者 可通过此平台对系统图书资料进行管理与相关操作； 发布日志模块：发布日志即系统内通知发送，通过点对点，点对面的方式进行播报系统 信息。根据系统的设计要求，以及对于社会服务的详细分析，将采用以下两种方案： 1）实地考察所处地区图书管理行业的市场及其现状，咨询本地有关图书管理相关行业， 根据具体的需求和服务内容进行详细的系统需求分析，延展出个性化的系统服务设计，经过 多次讨论以及改善自己的设计需求去完成本次研究内容。 2）通过互联网具体问题具体分析，通过数据的对比去研究本次课题内容，并且进行细节 性问题的探讨，根据社会需求去定制专属于互联网时代的系统，指定符合现代互联网下的图 书管理系统需求分析。 通过以上两个方案，可以展开本地化系统探讨，以及互联化下的系统探讨研究，由于是 基于 Web 端，本设计依旧采用 B/S 架构,系统功能模块分为 6 个主要部分,分别是用户系统权 限控制管理，用户管理模块，图书管理模块，资料管理模块，常用功能模块，发布日志模块。

## 系统实现结果
  系统实现是在项目需求分析后的成果下依据相关体系结构和实现技术对需求进行拆分和建模，通过计算机语言实现功能实现，具体实现结果如下介绍所示，最后在软件测试的环节下验证系统可行性。
图书资料管理主要涉及到图书资料的录入，信息修改、相关资源文件管理，以及图书采购申请、采购申请模板构建、采购流程处理、审批过程展示、图书资源入库、库存构建（书架信息、书架层信息和库位信息完善），构建库位与图书的一对一关系。

### 图书基本信息维护
  当前模块涉及到图书的相关信息保存，保存图书相关线索信息后，可通过相应操作提交其所配备的附件信息，包括各种类型的文件类型，譬如pdf、各种格式的高清图片以及一些相关视频等重要资源，通过保存文件信息到附件表，我们可以在每次搜索图书室查询到这些文件列表，用于提供丰富的阅览和管理体验。
对图书进行操作时，可以通不通搜索类型查询图书，也可以对图书进行动态调整，新增图书时新增人就是当前操作人。如图5.1所示。

![图片](https://user-images.githubusercontent.com/73671105/176438736-09cd9b51-41de-4a6e-b166-acf801361832.png)

  新增图书时，操作人员可以分别进行两步操作，当然在新增图书时我们首先需要提交新增图书的基本信息，比如说名称、图书类别之类的信息，还有一些其他相关内容，我们提交好这些内容后，此时可以在列表中看到新增的信息，同时操作人依然可以对这个图书的基本信息进行丰富化，在右侧找到资源管理就可以提交图书的相关附件。
图书信息管理为图书的基本信息录入，包含其基本信息以及图书类别、图书标签的详细信息字段，如图5.2所示。

![图片](https://user-images.githubusercontent.com/73671105/176439042-5613bda0-d6de-4d4a-9751-a7a6171d4ca6.png)

  图书基本信息是对图书的详情以及图书资源包含出版社信息、作者信息的展示，如图5.3所示。
  
![图片](https://user-images.githubusercontent.com/73671105/176439157-223162b6-ab5b-4a54-93bd-284848aa316a.png)

  图书资源信息包含图书的图片资源以及文件pdf的文本格式文件，甚至管理者可删上传图片的视频资源，资源管理如图5.4所示。
  
![图片](https://user-images.githubusercontent.com/73671105/176439262-87a625c9-627a-483e-8bc0-dd123bc1c7f9.png)

### 图书库存信息维护
  库存管理的实现涉及到三个主要因素，根据实际情况展开的定夺，将库存体系划分为部门下的书架实体、书架层实体、库位实体，部门作为分类起点，一个部门与书架的对应关系为一对多，同样书架与书架层的对应关系也是一对多，同时书架层可包含多个库位，这样便将图书管理细化到了具体的库位，对应图书的出入、借还都作用在了库存层面上，极大地提高了管理的精准度和充分度。管理者可在自己的部门权限下管理相应部门的书架信息，其中包括书架的添减改查，如图5.5所示。
  
![图片](https://user-images.githubusercontent.com/73671105/176439406-3f0a4041-5d86-4af7-9b25-19bbc111d862.png)

  上图展示了图书列表信息，可通过列表右侧按钮打开图书书架基本信息详情，同时可对基本信息详情进行修改，如图5.6所示。
  
![图片](https://user-images.githubusercontent.com/73671105/176439513-c06fb9fa-abed-4ec9-8c17-f675b9c18aa8.png)

  其次，相应的图书管理机构的信息操作人员可以对相应书架的书架层进行增减改查，通过维护书架层信息来对库存结构进行优化和调节，为了方便操作人员进行业务管理，书架层与书架采用主子表进行数据展示，展开主表（书架信息）即可查看到期子表（书架层）信息，选择主表即可对当前书架的书架层进行操作（增删改查等），书架层信息列表如图5.7所示。
  
![图片](https://user-images.githubusercontent.com/73671105/176439626-e4ffc152-433c-4584-b35d-245fcdbc0fe1.png)

  库位列表表示库位的具体信息，查找库位列表依次需要从部门->存书书架->所在书架层中过滤出库位列表，库位列表展示大图表示当前库位所存放图书首图，若库位空闲，则表示当前库位展示大图为一张默认图片，这样更有利于库位状态的实时展示，库位列表图如图5.8所示。
  
![图片](https://user-images.githubusercontent.com/73671105/176439720-be58523d-eb7b-4b7a-93a9-e79ddca204ee.png)

  库位详情图包含库位的具体信息，在库位详情操作下可修改库位基本信息以及库位的操作流水信息，同时可查看库位的存书信息，并可实时浏览图书信息包括下载图书相关资源，若暂无存书信息，则存书信息为一个默认的空状态。库位详情信息如图5.9所示。
  
![图片](https://user-images.githubusercontent.com/73671105/176439823-37252ba0-3e14-4c93-8a68-b0571c183600.png)

### 图书采购信息维护
  审批流程在本系统主要涉及的业务为图书采购，通过创建审批流程模板（流程模板的持久化保存类型为JSON数据格式，通过设计流程结构生成JSON来实现流程信息）来构建相应的采购流程，从而进行图书入库操作，审批流程模板的实现通过构建流程处理构成来生成流程处理链路，如图5.10所示。
  
![图片](https://user-images.githubusercontent.com/73671105/176439922-4898dfd2-b28a-41af-8a3a-1d8852771599.png)
  
  流程维护通过维护结点信息实现，通过定义结点处理人进行结点的定义，处理人选择可根据用户角色、部门分类等方式筛选（多选方式），流程维护图如图5.11所示。
  
![图片](https://user-images.githubusercontent.com/73671105/176439955-d9701225-b8d6-4a4a-9d77-b131b6e31229.png)
  
  以下代码主要涉及对流程信息的解析与处理，进而对图书采购申请审批流程模板进行数据操作：
  
```java
/**获取审批流第一个结点*/
public static PurchaseNode getFirstNode(TemplateParam param){
List<String>fromList = new ArrayList<>();
List<String>toList=new ArrayList<>();
List<PurchaseNode>nodes=param.getNodeList();
List<PurchaserLine>lines=param.getLineList();
if(null==lines||lines.size()<1) return null;
String star=lines.get(0).getFrom();
for(PurchaserLine line:lines){
fromList.add(line.getFrom());
toList.add(line.getTo());
}
for (String s:fromList)
if(!toList.contains(s))
star=s;break;
for(PurchaseNode node:nodes)
if(star.equals(node.getId()))
return node;
return null;
}
/**根据当前结点id获取当前结点子节点结点*/
public static List<NextNode> getNextNode(TemplateParam param,String curNodeId){
List<NextNode>nextNodeIdList=new ArrayList<>();
List<PurchaseNode>nodes=param.getNodeList();
List<PurchaserLine>lines=param.getLineList();
if (null==lines||lines.size()<1) return null;
for(PurchaserLine line:lines){
if(curNodeId.equals(line.getFrom()))
nextNodeIdList.add(new NextNode(
line.getLabel(),getCurNode(param,line.getTo())));}
return nextNodeIdList;
}
/**根据当前结点id获取当前结点*/
public static PurchaseNode getCurNode(TemplateParam param,String curNodeId){
List<PurchaseNode>nodes=param.getNodeList();
if(null==nodes||nodes.size()<1) return null;
for(PurchaseNode node:nodes)
if(curNodeId.equals(node.getId()))
return node;
return null;
}
```

  图书出入库基于审批通过的采购单进行处理，如某图书管理员申请了一批采购项目，经过相关流程处理后申请通过，此时相关人员就可通过图书入库进行图书的入库，当前提供自动快速入库，入库的依据是根据采购项目（采购图书）的图书类别以及采购单发起的部门，在相关部门下找到存书类别与采购图书相同的图书类别的库位进行自动入库，如果库位不够就立即提示相应部门下存放相应图书类别的库位不足，如果库位存在就将图书入库，并减少采购单下相关采购项目的待入库数，同时库位设置为已有图书，图书采购单列表如图5.12所示。
  
![图片](https://user-images.githubusercontent.com/73671105/176440207-df44335f-ae3d-40ca-9efa-31098b1dc5e2.png)
  采购单详情信息包括采购部门、采购单的采购审批流程模板的选择、以及一个到期时间，同时包含采购项目，如图5.13所示。
  
![图片](https://user-images.githubusercontent.com/73671105/176440248-853b8bf2-4c62-40f7-9a7d-2e27cc49bb37.png)
  待入库采购单列表表示已经通过审批流程，同时流程处理已经完成，操作人员（图书管理员）可通过一键快速入库的操作进行图书入库，根据采购项目进行必要操作，图书入库如图5.14所示。
  
![图片](https://user-images.githubusercontent.com/73671105/176440287-521058b0-9f5a-4755-9609-056ba3fba0a5.png)
  
### 常用功能模块
  此图书管理系统下的常用功能模块包含图书借阅与归还操作，主要的面向群体（系统角色）为游客或普通用户。操作人要在图书搜索的操作下输入关键字进行搜索，关键字的搜索范围包含图书管理机构（图书馆）、图书名称（模糊搜索，其他搜索同此搜索类型）、图书类别（国家图书分类法下的一千多累图书分类）等其他相关搜索字段。用户单击图书卡片后可展开图书详情，通过详情可查看当前图书是否有图书库存，并获取此图书被入库机构及其部门，可查看对应库存状态（库存数量等），获取图书借阅码在图书借阅下借阅对应图书，图书搜索如图5.15所示。
  
![图片](https://user-images.githubusercontent.com/73671105/176440410-a4c6677c-a037-48b5-ac1b-37c6fb1cbe90.png)
  
  图书借阅是基于借阅码进行的操作，通过实时借阅码进行图书查询，获取到的图书借阅码只能本人操作并且操作一次后失效，同时借阅码有一定的有效时间，超时需重新获取，借阅失败后同样会失效，完成当前业务操作主要基于Redis进行的，对用户Id和图书借阅码进行关联，并设置过期时间。图书借阅如图5.16所示。
  
![图片](https://user-images.githubusercontent.com/73671105/176440446-41fa8984-550f-4382-ac97-53d2190831aa.png)
  
  图书借阅列表展示的是当前本人借阅图书的列表信息，展示的图书表示借阅但未归还的图书，可在列表中一键归还图书，归还图书后，重新更改库存状态，同时生成图书归还流行信息，图书借阅列表如图5.17所示。
  
![图片](https://user-images.githubusercontent.com/73671105/176440485-911b62f1-f3f5-4466-9d45-e57552bd9420.png)
  
### 系统基础信息维护
  出版社信息作用域图书信息的维护，降低信息冗余，提高信息的灵活性，系统录入一定的出版社信息可以一定程度上丰富图书信息，出版社的分页结果列表信息如图5.18所示。
  
![图片](https://user-images.githubusercontent.com/73671105/176440538-096c56cd-d472-4063-9c7b-4003afaedf53.png)
  
  出版社的添加操作包括出版社的名称以及出版社的地理地址等基本信息，出版社的信息新增与修改操作如图5.19所示。
  
![图片](https://user-images.githubusercontent.com/73671105/176440573-1165eee4-19d1-45ea-b7c2-7139d66fa089.png)
  
  图书管理机构作为本系统的以及部门，其具备信息管理的功能特点，相关图书管理机构可拥有自己的部门信息、图书信息、人员信息、库存信息等系统基本构成要素，图书管理机构分页展示列表如图5.20所示。
  
![图片](https://user-images.githubusercontent.com/73671105/176440618-78bfbef2-2ffb-45b2-9ea4-adf5b4f2a276.png)

  图书管理机构的基本信息维护，其中包含了图书管理机构的图片介绍（暂定有且仅有五个图片介绍），其中详细信息中包含了部分信息以及一个管理机构标签，我们采当前较为流行的中文分词器HanLP(一种自然语言处理工具),对图书管理机构的全部信息合并为一段文字后进行关键字分词处理，获得的关键信息返回分词结果作为管理机构简介中的标签，并保存到redis后进行持久化，方便下次查看。图书管理机构详情如图5.24所示，管理机构介绍图片更改如图5.21所示。
  
![图片](https://user-images.githubusercontent.com/73671105/176440688-54e70c21-cbc3-4654-be3b-aa16728ef6e9.png)
  
  以下代码主要涉及分词解析与处理，进而通过HanLP对自然语言进行处理返回图书管理机构相关关键字：
  
  ```java
  /***分析字符串获取关键词*/
@Log(title="分析字符串获取关键词",businessType=BusinessType.UPDATE)
@PostMapping("/analysisStr")
public AjaxResult analysisStr(@RequestBody LibraryMechanism mechanism){
String tagKey="mechanismTag_"
+mechanism.getId()+"_"+TableTypeEnums.MECHANISM.getCode();
//从redis缓存中获取
List<String>tags=redisCache.getCacheList(tagKey);
//没有获取到
if(null==tags||tags.size()<=0){
//先查询信息
 mechanism.setStatus(StatusEnums.ONE.getDesc());
 LibraryMechanism mechanismItem=
libraryMechanismService.selectLibraryMechanismById(mechanism.getId());
 String mark=mechanismItem.getMark();
//生成关键字列表
tags=Tokenizer.getTokenizerValue(mark);
if(null==tags||tags.size()<=0){
 tags=new ArrayList<>();
 tags.add("暂无");
}
//添加关键字到redis
redisCache.setCacheList(tagKey,tags);
//设置过期时间，默认1800秒
redisCache.expire(tagKey,this.mechanismTagTimeOut);
}
return AjaxResult.success(tags);}
```
作者信息作为图书信息的补充，显而易见它也具备它存在的价值所在，等同于出版社信息，依然是作为图书资源的扩充与丰富，分页显示后的作者列表信息展示如图5.22所示。

![图片](https://user-images.githubusercontent.com/73671105/176440809-4fbe9053-09f5-4738-abdd-52b847e76d27.png)

### 用户信息维护
  用户管理模块座位系统的基础模块，其中包含了用户列表（向系统添加系统用户基本信息，修改系统下用户的详情信息等）、系统下用户的访问菜单权限（基于不同角色处理的权限类别）、系统下用户的部门访问级别设定（通过调节不同用户对部门的访问级别实现信息隔离以及功能分区）。用户管理是本图书管理系统的基础模块之一，不同用户具备多种角色，不同用户也具备不同职位类别、不同用户同时也可以拥有多个部门访问权限，用户列表如图5.23所示。
  
![图片](https://user-images.githubusercontent.com/73671105/176440867-58670f07-036e-4538-b3fd-e27db0b74801.png)
  
### 部门与角色信息维护
  部门信息在本系统不仅区分于用户所在位置，更重要的一点就是区分不同功能划分，根据部门来划分系统用户的可见功能，部门列表如图5.24所示。
  
![图片](https://user-images.githubusercontent.com/73671105/176440960-955c504e-cc1f-4c43-933a-ac472d1605b7.png)
  
  角色在本系统充当菜单访问、功能控制、资源管理等不同细节需求方面的区分，不同角色的用户具有不同的系统功能区分度，角色新增如图5.25所示。
  
![图片](https://user-images.githubusercontent.com/73671105/176441002-36560481-a29f-40a4-8cbc-6397255d1f15.png)

  部门的权限管理在本系统中意蕴丰富，部门权限作用范围包括库存管理、流程处理、图书管理等重要功能模块，部门权限的设定精确到每一个系统用户，部门权限设置如图5.26所示。
  
![图片](https://user-images.githubusercontent.com/73671105/176441035-ebf305ac-7d66-4536-b7cb-7acfc4403ea3.png)
  
### 通知与公告信息维护
  通知功能作为系统下用户与系统的交互，系统用户与用户的枢纽，在本系统中起到举住轻重的程度，用户可以通过通知管理对全体管理员或图书使用者进行信息公告或消息通知，每次用户点击通知菜单时可查看相关通知详细信息，详细的分页后通知列表如图5.27所示。
  
![图片](https://user-images.githubusercontent.com/73671105/176441107-379bc183-4fb2-4822-b444-7e69344d68c4.png)
  
  通知发布主要面向的操作对象时系统管理员，系统管理员可以通过发布消息或通知递送给选择的消息接收主体，编辑通知图如图5.28所示。
  
![图片](https://user-images.githubusercontent.com/73671105/176441134-061a9582-c093-49e7-bb26-e85c979d411e.png)
  
## 写在最后
  在本系统实现章节下，我们深入的了解了系统功能的实现有测试环节，在一定的数据处理方面，进行了较为深刻的探讨，充分利用了相关实现技术，在Ruoyi-vue前后端分离模式下，利用敏捷开发流程（重点关注主要需求，对次要需求采取后续迭代处理的方式）下快速构建了完善的功能体系，利用HanLP对自然语言进行处理（中文分词）、基于easy-flow（基于vue+elementUI实现）实现的工作流等一系列技术加持下实现系统功能。
## 精选文件上传结构代码：
```java
public interface FileHandel{
/**单文件上传**/
public default AttachFile upload(MultipartFile file,FileKey fileToken)
throws Exception{
		return this.upload(file,null,fileToken,null);
}
/**多文件上传**/
public default List<AttachFile> upload(
MultipartFile[] files,
		FileKey fileToken) throws Exception{
		return this.upload(files,fileToken,null);
	}
/**多文件上传定义文件夹**/
public default List<AttachFile> upload(
MultipartFile[] files,
FileKey fileToken,
FileTypeEnums folder) throws Exception{
		if(null == files||files.length<1){
			throw new ServiceException("提交无效文件信息");
		}
		List<AttachFile> fPoList=new ArrayList<>();
		Long groupID=UUID.randomUUID().getLeastSignificantBits();
		for (MultipartFile file : files){
			fPoList.add(this.upload(file,groupID,fileToken,folder));
}
		return fPoList;
	}
/**
*处理文件上传。实现类可重写
***/
public AttachFile upload(
MultipartFile file,
			Long groupId,
FileKey fileToken,
FileTypeEnums folder) throws Exception;
}
```
<div align='right'>-----毕业快乐！</div>
