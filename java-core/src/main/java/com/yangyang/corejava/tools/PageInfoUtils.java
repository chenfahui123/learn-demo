/**
 * Copyright 2014-2016 www.fangdd.com All Rights Reserved.
 * Author: Chen Shunyang <chenshunyang@fangdd.com>
 * Date:   2015年10月21日
 */
package com.yangyang.corejava.tools;


public class PageInfoUtils {
    /** 默认分页条数 **/
    public static final int DEF_PAGE_SIZE = 10;
    /** 默认分页条数 **/
    public static final int DEF_PER_PAGE_SIZE = 20;
    /** 默认第一页 **/
    public static final int DEF_PAGE = 1;

    /**
     * 根据传入的pageInfoObj对象以及总条数返回分页信息
     *
     * @param pageInfoObj
     * @param totalCnt
     * @return
     * @throws BizException 2015年6月19日 下午2:38:36 chenshunyang
     */
    public static PageInfoDto makePageInfo(Integer currentPage, Integer pageSize, Integer totalCnt)
            throws Exception {
        PageInfoDto pageInfoDto = new PageInfoDto();
        int pageNum = currentPage;
        if (pageNum == 0) {
            pageNum = DEF_PAGE;
        }
        if (pageSize == 0) {
            pageSize = DEF_PER_PAGE_SIZE;
        }
        int totalPage = 0;
        if (totalCnt != null && totalCnt != 0) {
            totalPage = totalCnt % pageSize != 0 ? totalCnt / pageSize + 1 : totalCnt / pageSize;
        }
        Integer startPageSize = 0; // 默认起始页
        if (startPageSize >= totalCnt) { // 取的页数不存在时则直接取首页
            startPageSize = 0;
        } else {
            startPageSize = pageSize * (pageNum - 1);
        }
        pageInfoDto.setPageNum(pageNum);
        pageInfoDto.setPageSize(pageSize);
        pageInfoDto.setTotalCnt(totalCnt);
        pageInfoDto.setTotalPage(totalPage);
        pageInfoDto.setStartPageSize(startPageSize);
        return pageInfoDto;
    }
}

class PageInfoDto {

    private Integer pageNum;//当前页数，即当前第几页
    private Integer pageSize;//每页的条数
    private Integer totalCnt;//总条数
    private Integer totalPage;//总页数
    private Integer startPageSize; //查询时，从哪一条开始

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalCnt() {
        return totalCnt;
    }

    public void setTotalCnt(Integer totalCnt) {
        this.totalCnt = totalCnt;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }


    public Integer getStartPageSize() {
        return startPageSize;
    }

    public void setStartPageSize(Integer startPageSize) {
        this.startPageSize = startPageSize;
    }
}
