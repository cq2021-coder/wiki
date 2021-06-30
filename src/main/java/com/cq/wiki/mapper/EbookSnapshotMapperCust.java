package com.cq.wiki.mapper;

import com.cq.wiki.resp.StatisticResp;

import java.util.List;

public interface EbookSnapshotMapperCust {

    void genSnapshot();

    List<StatisticResp> getStatistic();

    List<StatisticResp> get30Statistic();
}
