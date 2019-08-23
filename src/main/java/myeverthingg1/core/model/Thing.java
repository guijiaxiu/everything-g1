package myeverthingg1.core.model;

import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;
import lombok.Data;

import java.io.FileFilter;
@Data
public class Thing {
    //文件名称（不含路径）
    private String name;
    //文件路径
    private String path;
    //文件路径深度
    private Integer depth;
    //文件类型
    private FileType fileType;

}
