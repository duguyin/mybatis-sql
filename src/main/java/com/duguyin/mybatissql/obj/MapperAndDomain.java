package com.duguyin.mybatissql.obj;

/**
 * @ClassName MapperAndDomain
 * @Description
 * @Author LiuYin
 * @Date 2019/2/25 16:08
 */
public class MapperAndDomain {

    private Class mapperType;
    private Class domainType;

    public Class getMapperType() {
        return mapperType;
    }

    public void setMapperType(Class mapperType) {
        this.mapperType = mapperType;
    }

    public Class getDomainType() {
        return domainType;
    }

    public void setDomainType(Class domainType) {
        this.domainType = domainType;
    }
}
