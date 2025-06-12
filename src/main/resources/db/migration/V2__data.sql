INSERT INTO period VALUES (1, '09:00', '10:30');
INSERT INTO period VALUES (2, '10:40', '12:10');
INSERT INTO period VALUES (3, '13:00', '14:00');
INSERT INTO period VALUES (4, '14:40', '16:10');
INSERT INTO period VALUES (5, '16:20', '17:50');
INSERT INTO period VALUES (99, '00:00', '23:59');

insert into user (number, name, kana, password, email, role) values('T22001', '秋山正人', 'あきやままさと',  SHA2('1234', 256), 'njb-T22001@sist.ac.jp', 'student');
insert into user (number, name, kana, password, email, role) values('T22002', '秋山祐二', 'あきやまゆうじ', SHA2('1234', 256), 'njb-T22002@sist.ac.jp', 'student');
insert into user (number, name, kana, password, email, role) values('T22004', '伊倉旦陽', 'いぐらあさひ',  SHA2('1234', 256), 'njb-T22004@sist.ac.jp', 'student');
insert into user (number, name, kana, password, email, role) values('T22005', '泉陽翔', 'いずみはると',  SHA2('1234', 256), 'njb-T22005@sist.ac.jp', 'student');
insert into user (number, name, kana, password, email, role) values('T22006', '勝又夢叶', 'かつまたゆうと',  SHA2('1234', 256), 'njb-T22006@sist.ac.jp', 'student');
insert into user (number, name, kana, password, email, role) values('T22008', '岸本真征', 'きしもとしんせい',   SHA2('1234', 256), 'njb-T22008@sist.ac.jp', 'student');
insert into user (number, name, kana, password, email, role) values('T22010', '小林輝流', 'こばやしひかる',  SHA2('1234', 256), 'njb-T22010@sist.ac.jp', 'student');
insert into user (number, name, kana, password, email, role) values('T22011', '酒井竣', 'さかいしゅん',  SHA2('1234', 256), 'njb-T22011@sist.ac.jp', 'student');
insert into user (number, name, kana, password, email, role) values('T22012', '三瓶航太郎', 'さんぺいこうたろう',  SHA2('1234', 256), 'njb-T22012@sist.ac.jp', 'student');
insert into user (number, name, kana, password, email, role) values('T22013', '杉山鉱生', 'すぎやまひろき',  SHA2('1234', 256), 'njb-T22013@sist.ac.jp', 'student');
insert into user (number, name, kana, password, email, role) values('T22014', '鈴木颯太', 'すずきそうた',  SHA2('1234', 256), 'njb-T22014@sist.ac.jp', 'student');
insert into user (number, name, kana, password, email, role) values('T22015', '鈴木陽大', 'すずきひなた',  SHA2('1234', 256), 'njb-T22015@sist.ac.jp', 'student');
insert into user (number, name, kana, password, email, role) values('T22016', '武井風樹', 'たけいふうき',  SHA2('1234', 256), 'njb-T22016@sist.ac.jp', 'student');
insert into user (number, name, kana, password, email, role) values('T22017', '山上結史', 'やまがみゆうし',  SHA2('1234', 256), 'njb-T22017@sist.ac.jp', 'student');
insert into user (number, name, kana, password, email, role) values('T22018', '山口瀬奈', 'やまぐちせな',  SHA2('1234', 256), 'njb-T22018@sist.ac.jp', 'student');
insert into user (number, name, kana, password, email, role) values('T22019', '由布美織', 'ゆふみおり',  SHA2('1234', 256), 'njb-T22019@sist.ac.jp', 'student');
insert into user (number, name, kana, password, email, role) values('T22020', '湯山公晴', 'ゆやまこうせい',  SHA2('1234', 256), 'njb-T22020@sist.ac.jp', 'student');
insert into user (number, name, kana, password, email, role) values('T22021', '渡辺爽流', 'わたなべそうる',  SHA2('1234', 256), 'njb-T22021@sist.ac.jp', 'student');
insert into user (number, name, kana, password, email, role) values('T22022', '綿抜唯織', 'わたぬきいおり',  SHA2('1234', 256), 'njb-T22022@sist.ac.jp', 'student');

INSERT INTO pc (name, serial_number, status) VALUES
('PC-001', 'SN100001', 'available'),
('PC-002', 'SN100002', 'available'),
('PC-003', 'SN100003', 'available'),
('PC-004', 'SN100004', 'available'),
('PC-005', 'SN100005', 'available');
