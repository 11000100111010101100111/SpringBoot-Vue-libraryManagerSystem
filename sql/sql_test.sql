SELECT bprs.* FROM `base_purchase_requisition_sub` AS bprs 
LEFT JOIN `base_purchase_requisition` AS bpr ON bprs.group_id = bpr.id
WHERE bprs.status='1' AND bprs.is_del = '0' 
  AND bpr.`term_validity`>NOW() 
  AND bprs.`purchase_number`>bprs.`purchase_over_number`
  AND bpr.`is_del`='0'
  AND bpr.`status`='1'
  AND bpr.`purchase_status`='3'
  AND bpr.`approve_dept` IN (SELECT bda.dept_id FROM `base_dept_authority` AS bda WHERE bda.user_id = 1 AND bda.status='1' AND bda.is_del ='0')
  
  
  
  SELECT * FROM `base_purchase_requisition_sub` WHERE `batch_id`=1498943983072706560
  
  
  SELECT * FROM `library_stock` AS ls LEFT JOIN `library_stock_storey` AS lss ON ls.`store_id` = lss.id
  WHERE ls.status='1' AND ls.is_del='0' AND lss.status='1' AND lss.is_del = '0'
	AND ls.`mechanism` = 106 AND `book_type`=1
	
	
SELECT * FROM `base_book_class_cn` WHERE `name` = '计算机'

UPDATE `base_book_class_cn` SET `name` = '世界-F091' WHERE id = 435
UPDATE `base_book_class_cn` SET `name` = '世界-G11' WHERE id = 640
UPDATE `base_book_class_cn` SET `name` = '世界-G51' WHERE id = 689

UPDATE `base_book_class_cn` SET `name` = '计算机G623.58' WHERE id = 715;
UPDATE `base_book_class_cn` SET `name` = '世界G633.67' WHERE id = 769;



SELECT 
lb.id,
            lb.`name`,
            lb.code,
            lb.`type`,
            lb.leavel,
            lb.claz,
            lb.author,
            lb.mechanism,
            lb.publication_date,
            lb.version,
            lb.circulation,
            lb.ISBN,
            lb.series,
            lb.key_words,
            lb.price,
            lb.mark,
            lb.other,
            lb.is_del,
            lb.status,
            lb.create_personal,
            lb.create_time,
            lb.update_time,
            lb.update_personal,
            la.name,
            la.country,
            la.nick_name,
            la.english_name,
            la.synopsis,
            la.identity,
            bbcc.code,
            bbcc.name,
            lp.name,
            lp.addr,
            lp.mark,
            lp.email,
            lp.phone,
            sd.dept_id,
            sd.dept_name,
            sd.leader,
            sd.phone,
            sd.email,
            (SELECT COUNT(0) FROM book_stock WHERE book_stock.is_del='0' AND book_stock.status='1') AS num1,
            (SELECT COUNT(0) FROM book_stock WHERE book_stock.is_del='0' AND book_stock.status='1' AND has_loan='0') AS num2
 FROM `library_book` AS lb
	LEFT JOIN `library_auth` AS la ON la.id = lb.author AND la.status='1' AND la.is_del='0'
	LEFT JOIN `base_book_class_cn` AS bbcc ON bbcc.id = lb.claz AND bbcc.status='1'
	LEFT JOIN `library_press` AS lp ON lp.id = lb.`mechanism` AND lp.status='1' AND lp.is_del='0'
	LEFT JOIN `book_stock` AS bs ON bs.book_id = lb.id AND bs.status='1' AND bs.is_del='0'
	LEFT JOIN `sys_dept` AS sd ON bs.`mechanism_id` = sd.dept_id AND sd.status='0' AND sd.del_flag='0'
WHERE
	(lb.name LIKE '%JAVA%' OR
	lb.series LIKE '%JAVA%' OR 
	lb.key_words LIKE '%JAVA%' OR
	lb.mark LIKE '%JAVA%' OR
	la.name LIKE '%JAVA%' OR
	la.country LIKE '%JAVA%' OR
	la.nick_name LIKE '%JAVA%' OR
	la.english_name LIKE '%JAVA%' OR
	la.synopsis LIKE '%JAVA%' OR
	la.identity LIKE '%JAVA%' OR
	bbcc.name LIKE '%JAVA%' OR
	bbcc.ali_name LIKE '%JAVA%' OR
	lp.name LIKE '%JAVA%' OR
	lp.addr LIKE '%JAVA%' OR
	lp.mark LIKE '%JAVA%' OR
	lp.email LIKE '%JAVA%' OR
	sd.dept_name LIKE '%JAVA%')
	AND lb.status = '1'
	AND lb.is_del='0' 
	
	
	AND lb.id IN (SELECT DISTINCT(bs.book_id) FROM book_stock AS bs WHERE bs.status='1' AND bs.is_del='0')
	
	GROUP BY lb.id	
	
UPDATE `book_stock` SET `status`=1



 
SELECT * FROM sys_dept WHERE dept_id = ( SUBSTRING_INDEX( SUBSTRING_INDEX( ( SELECT ancestors FROM sys_dept WHERE dept_id = 101 ),',',2 ),',',-1 ) ) 

SELECT * FROM sys_dept WHERE FIND_IN_SET(dept_id,(SELECT ancestors FROM sys_dept WHERE dept_id = 112))
 
SELECT 
    SUBSTRING_INDEX(SUBSTRING_INDEX('7654,7698,7782,7788',',',ancestors+1),',',-1) AS num 
FROM 
    sys_dept.ancestors 
WHERE 
    ancestors < LENGTH('7654,7698,7782,7788')-LENGTH(REPLACE('7654,7698,7782,7788',',',''))+1


SELECT 
            lb.id AS bookId,
            lb.`name` AS bookName,
            lb.code AS bookCode,
            lb.`type` AS bookType,
            lb.leavel AS bookLeavel,
            lb.claz AS bookClaz,
            lb.author AS bookAuthor,
            lb.mechanism AS bookMechanism,
            lb.publication_date AS bookPublicationData,
            lb.version AS bookVersion,
            lb.circulation AS bookCirculation,
            lb.ISBN AS bookISBN,
            lb.series AS bookSeries,
            lb.key_words AS bookKeyWords,
            lb.price AS bookPrice,
            lb.mark AS bookMark,
            la.name AS authName,
            la.country AS authCountry,
            la.nick_name AS authNickName,
            la.english_name AS authEnglishName,
            la.synopsis AS authSynopsis,
            la.identity AS authIdentity,
            bbcc.code AS clazCode,
            bbcc.name AS clazName,
            lp.name AS pressName,
            lp.addr AS pressAddr,
            lp.mark AS pressMark,
            lp.email AS pressEmail,
            lp.phone AS pressPhone,
            sd.dept_id AS deptId,
            sd.dept_name AS deptName,
            sd.leader AS deptLeader,
            sd.phone AS deptPhone,
            sd.email AS deptEmail,
            (SELECT COUNT(0) FROM book_stock WHERE book_stock.is_del='0' AND book_stock.status='1' AND book_stock.book_id = lb.id) AS saveNumber,
            (SELECT COUNT(0) FROM book_stock WHERE book_stock.is_del='0' AND book_stock.status='1' AND has_loan='0' AND book_stock.book_id = lb.id) AS nowNumber
        FROM `library_book` AS lb
                LEFT JOIN `library_auth` AS la ON la.id = lb.author AND la.status='1' AND la.is_del='0'
                LEFT JOIN `base_book_class_cn` AS bbcc ON bbcc.id = lb.claz AND bbcc.status='1'
                LEFT JOIN `library_press` AS lp ON lp.id = lb.`mechanism` AND lp.status='1' AND lp.is_del='0'
                LEFT JOIN `book_stock` AS bs ON bs.book_id = lb.id AND bs.status='1' AND bs.is_del='0'
                LEFT JOIN `sys_dept` AS sd ON bs.`mechanism_id`=sd.dept_id AND sd.status='0' AND sd.del_flag='0'
            WHERE
                    (
                        lb.name LIKE '%南昌%' OR
                        lb.series LIKE '%南昌%' OR
                        lb.key_words LIKE '%南昌%' OR
                        lb.mark LIKE '%南昌%' OR
                        la.name LIKE '%南昌%' OR
                        la.country LIKE '%南昌%' OR
                        la.nick_name LIKE '%南昌%' OR
                        la.english_name LIKE '%南昌%' OR
                        la.synopsis LIKE '%南昌%' OR
                        la.identity LIKE '%南昌%' OR
                        bbcc.name LIKE '%南昌%' OR
                        bbcc.ali_name LIKE '%南昌%' OR
                        lp.name LIKE '%南昌%' OR
                        lp.addr LIKE '%南昌%' OR
                        lp.mark LIKE '%南昌%' OR
                        lp.email LIKE '%南昌%' OR
                        sd.dept_name LIKE '%南昌%'
                    )
            AND lb.status = '1'
            AND lb.is_del='0'


SELECT id FROM `book_stock` WHERE book_id = 3 AND is_del='0' AND `status`='1' LIMIT 1


