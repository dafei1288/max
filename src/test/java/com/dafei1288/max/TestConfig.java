package com.dafei1288.max;

import com.dafei1288.max.config.MaxConfig;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class TestConfig {
    public static void main(String[] args) throws IOException {
        System.out.println(MaxConfig.getInstance().getSurnameComparatorRuleComparatorRule());
        String res = "赵钱孙李，周吴郑王。\n" +
                "冯陈褚卫，蒋沈韩杨。\n" +
                "朱秦尤许，何吕施张。\n" +
                "孔曹严华，金魏陶姜。\n" +
                "戚谢邹喻，柏水窦章。\n" +
                "云苏潘葛，奚范彭郎。\n" +
                "鲁韦昌马，苗凤花方。\n" +
                "俞任袁柳，酆鲍史唐。\n" +
                "费廉岑薛，雷贺倪汤。\n" +
                "滕殷罗毕，郝邬安常。\n" +
                "乐于时傅，皮卞齐康。\n" +
                "伍余元卜，顾孟平黄。\n" +
                "和穆萧尹，姚邵湛汪。\n" +
                "祁毛禹狄，米贝明臧。\n" +
                "计伏成戴，谈宋茅庞。\n" +
                "熊纪舒屈，项祝董梁。\n" +
                "杜阮蓝闵，席季麻强。\n" +
                "贾路娄危，江童颜郭。\n" +
                "梅盛林刁，钟徐邱骆。\n" +
                "高夏蔡田，樊胡凌霍。\n" +
                "虞万支柯，昝管卢莫。\n" +
                "经房裘缪，干解应宗。\n" +
                "丁宣贲邓，郁单杭洪。\n" +
                "包诸左石，崔吉钮龚。\n" +
                "程嵇邢滑，裴陆荣翁。\n" +
                "荀羊於惠，甄曲家封。\n" +
                "芮羿储靳，汲邴糜松。\n" +
                "井段富巫，乌焦巴弓。\n" +
                "牧隗山谷，车侯宓蓬。\n" +
                "全郗班仰，秋仲伊宫。\n" +
                "宁仇栾暴，甘钭厉戎。\n" +
                "祖武符刘，景詹束龙。\n" +
                "叶幸司韶，郜黎蓟薄。\n" +
                "印宿白怀，蒲邰从鄂。\n" +
                "索咸籍赖，卓蔺屠蒙。\n" +
                "池乔阴鬱，胥能苍双。\n" +
                "闻莘党翟，谭贡劳逄。\n" +
                "姬申扶堵，冉宰郦雍。\n" +
                "卻璩桑桂，濮牛寿通。\n" +
                "边扈燕冀，郏浦尚农。\n" +
                "温别庄晏，柴瞿阎充。\n" +
                "慕连茹习，宦艾鱼容。\n" +
                "向古易慎，戈廖庾终。\n" +
                "暨居衡步，都耿满弘。\n" +
                "匡国文寇，广禄阙东。\n" +
                "欧殳沃利，蔚越夔隆。\n" +
                "师巩厍聂，晁勾敖融。\n" +
                "冷訾辛阚，那简饶空。\n" +
                "曾毋沙乜，养鞠须丰。\n" +
                "巢关蒯相，查后荆红。\n" +
                "游竺权逯，盖益桓公。\n" +
                "万俟司马，上官欧阳。\n" +
                "夏侯诸葛，闻人东方。\n" +
                "赫连皇甫，尉迟公羊。\n" +
                "澹台公冶，宗政濮阳。\n" +
                "淳于单于，太叔申屠。\n" +
                "公孙仲孙，轩辕令狐。\n" +
                "钟离宇文，长孙慕容。\n" +
                "鲜于闾丘，司徒司空。\n" +
                "丌官司寇，仉督子车。\n" +
                "颛孙端木，巫马公西。\n" +
                "漆雕乐正，壤驷公良。\n" +
                "拓跋夹谷，宰父谷梁。\n" +
                "晋楚闫法，汝鄢涂钦。\n" +
                "段干百里，东郭南门。\n" +
                "呼延归海，羊舌微生。\n" +
                "岳帅缑亢，况郈有琴。\n" +
                "梁丘左丘，东门西门。\n" +
                "商牟佘佴，伯赏南宫。\n" +
                "墨哈谯笪，年爱阳佟。\n" +
                "第五言福，百家姓终。";
        res = res.replaceAll("，","").replaceAll("。","").replaceAll("\n","");
        System.out.println(res);

//        List chars = Arrays.asList(res.toCharArray());

//        chars.stream().forEach(it->{System.out.println((char)it);});
//
        StringBuilder sb = new StringBuilder();
        for(int i =0;i<res.toCharArray().length;i++){
            char c = res.toCharArray()[i];
            System.out.println(c);
            sb.append(c).append("<");
        }
        System.out.println(sb.toString());

    }
}
