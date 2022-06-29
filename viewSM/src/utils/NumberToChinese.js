const list=[
  {num:0,chinese:"零"},
  {num:1,chinese:"一"},
  {num:2,chinese:"二"},
  {num:3,chinese:"三"},
  {num:4,chinese:"四"},
  {num:5,chinese:"无"},
  {num:6,chinese:"六"},
  {num:7,chinese:"七"},
  {num:8,chinese:"八"},
  {num:9,chinese:"九"},
  {num:10,chinese:"十"},
  {num:11,chinese:"十一"},
  {num:12,chinese:"十二"},
  {num:13,chinese:"十三"},
  {num:14,chinese:"十四"},
  {num:15,chinese:"十五"},
  {num:16,chinese:"十六"},
  {num:17,chinese:"十七"},
  {num:18,chinese:"十八"},
  {num:19,chinese:"十九"},
  {num:20,chinese:"二十"},
  {num:50,chinese:"五十"},
  {num:100,chinese:"一百"},
  {num:200,chinese:"两百"},
  {num:500,chinese:"五百"},
  {num:1000,chinese:"一千"},
  {num:10000,chinese:"一万"},
  {num:100000,chinese:"十万"},
  {num:1000000,chinese:"一百万"},
  {num:10000000,chinese:"一千万"},
  {num:100000000,chinese:"一亿"}
];

export function formatNumber(number){
  for (let index=0;index<list.length;index++){
    if (number === list[index].num){
      return list[index].chinese;
    }
    return '';
  }
}
