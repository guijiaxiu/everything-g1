package myeverthingg1.core.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 *FileType模型类表示文件的扩展名归类
 *
 */


// 1. 枚举类型
public enum FileType {
    IMG("jpg","jpeg","png","bmp","gif"),
    DOC("doc","docx","pdf","pptx","xls","txt"),
    BIN("exe","jar","sh","msi"),
    ARCHIVE("zip","rar"),
    OTHER("*");

    private Set<String> extend = new HashSet<>();
    //可变参数
    FileType(String...extend){
        //2. 数组转为集合
        this.extend.addAll(Arrays.asList(extend));
    }

    //  3. 根据扩展名寻找FileType
    public static FileType lookupByExtend(String extend){
        for(FileType fileType:FileType.values()){
            //如果包含返回文件类型
            if(fileType.extend.contains(extend)){
                return fileType;
            }
        }
        return FileType.OTHER;
    }

    //4. 传入后缀名，判断是什么类型
    public static FileType lookupByName(String name){
        for(FileType fileType:FileType.values()){
            if(fileType.name().equals(name)){
                return fileType;
            }
        }
        return FileType.OTHER;
    }

    public static void main(String[] args) {
        //根据文件类型寻找寻找枚举的name
        System.out.println(FileType.lookupByExtend("exe"));//bin
        System.out.println(FileType.lookupByExtend("md"));//other
        //在文件类型中 根据枚举name寻找是否存在
        System.out.println(FileType.lookupByName("BIN"));//bin
        System.out.println(FileType.lookupByName("SHELL"));//other
    }
}
