package com.github.zxh.classpy.classfile.attribute;

import com.github.zxh.classpy.classfile.ClassComponent;

/*
MethodParameters_attribute {
    u2 attribute_name_index;
    u4 attribute_length;
    u1 parameters_count;
    {   u2 name_index;
        u2 access_flags;
    } parameters[parameters_count];
}
 */
public class MethodParametersAttribute extends AttributeInfo {

    {
        super.addU1("parametersCount");
        super.addTable("parameters", ParameterInfo.class);
    }

    
    public static class ParameterInfo extends ClassComponent {

        {
            u2("nameIndex");
            u2("accessFlags");
        }
        
    }
    
}
