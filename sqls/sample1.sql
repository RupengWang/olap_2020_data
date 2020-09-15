select cast(DS as varchar) as DS, count(distinct DISTINCT_ID2) as uv, count(DISTINCT_ID2) as pv from OLAP_2020_EVENT_TEST where DS <= '20200707' and DS >= '20200701' group by DS
union
select '合计', count(distinct DISTINCT_ID2), count(DISTINCT_ID2) from OLAP_2020_EVENT_TEST where DS <= '20200707' and DS >= '20200701'  order by ds