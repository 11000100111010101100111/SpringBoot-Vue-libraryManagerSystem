`nh-base``base_book_class_cn``ic_card_db`

UPDATE `base_book_class_cn` SET `parent_id` = -1 WHERE `class` = 0 AND `status`=1

UPDATE `base_book_class_cn` SET `parent_id` = 414 WHERE `class` = 1 AND `code` LIKE 'F%' AND `status`=1;
UPDATE `base_book_class_cn` SET `parent_id` = 637 WHERE `class` = 1 AND `code` LIKE 'G%' AND `status`=1;
UPDATE `base_book_class_cn` SET `parent_id` = 837 WHERE `class` = 1 AND `code` LIKE 'H%' AND `status`=1;
UPDATE `base_book_class_cn` SET `parent_id` = 856 WHERE `class` = 1 AND `code` LIKE 'I%' AND `status`=1;
UPDATE `base_book_class_cn` SET `parent_id` = 1027 WHERE `class` = 1 AND `code` LIKE 'J%' AND `status`=1;
UPDATE `base_book_class_cn` SET `parent_id` = 1038 WHERE `class` = 1 AND `code` LIKE 'K%' AND `status`=1;
UPDATE `base_book_class_cn` SET `parent_id` = 1051 WHERE `class` = 1 AND `code` LIKE 'N%' AND `status`=1;
UPDATE `base_book_class_cn` SET `parent_id` = 1063 WHERE `class` = 1 AND `code` LIKE 'O%' AND `status`=1;
UPDATE `base_book_class_cn` SET `parent_id` = 1069 WHERE `class` = 1 AND `code` LIKE 'P%' AND `status`=1;
UPDATE `base_book_class_cn` SET `parent_id` = 1077 WHERE `class` = 1 AND `code` LIKE 'Q%' AND `status`=1;
UPDATE `base_book_class_cn` SET `parent_id` = 1092 WHERE `class` = 1 AND `code` LIKE 'R%' AND `status`=1;
UPDATE `base_book_class_cn` SET `parent_id` = 1110 WHERE `class` = 1 AND `code` LIKE 'S%' AND `status`=1;
UPDATE `base_book_class_cn` SET `parent_id` = 1120 WHERE `class` = 1 AND `code` LIKE 'T%' AND `status`=1;
UPDATE `base_book_class_cn` SET `parent_id` = 1137 WHERE `class` = 1 AND `code` LIKE 'U%' AND `status`=1;
UPDATE `base_book_class_cn` SET `parent_id` = 1142 WHERE `class` = 1 AND `code` LIKE 'V%' AND `status`=1;
UPDATE `base_book_class_cn` SET `parent_id` = 1146 WHERE `class` = 1 AND `code` LIKE 'X%' AND `status`=1;
UPDATE `base_book_class_cn` SET `parent_id` = 1155 WHERE `class` = 1 AND `code` LIKE 'Z%' AND `status`=1;

UPDATE `base_book_class_cn` SET `del_time` = NULL,update_time = NULL

UPDATE `base_db_map` SET `create_person` = (SELECT `user_id` FROM `sys_user` WHERE `user_name`='admin')

SELECT * FROM `base_book_class_cn` WHERE `parent_id` = 'null'
SELECT * FROM `base_book_class_cn` WHERE  `parent_id` =  1 AND `status` = 1
SELECT id,`code`,`name` FROM `base_book_class_cn` WHERE `class` = 0

INSERT INTO `base_book_class_cn`
(`code`,`name`,`parent_id`,`class`,`ali_name`,`mark`)
VALUES
("#A","数值资料",-2,0,"数值资料","又称计量资料、定量资料；一般有度量衡单位；根据其取值是否连续分为连续型和离散型资料两类，前者可在实数范围内任意取值，如身高等；后者只取整数值，如某医院每年的死亡人数。"),
("#B","等级资料",-2,0,"等级资料","又称半定量资料或有序分类资料。"),
("#C","分类资料",-2,0,"分类资料","又称计数资料、定性资料或无序分类变量资料，亦称名义变量资料。其变量值是定性的，表现为互不相容的属性或类别，如实验结果的阳性阴性等。");