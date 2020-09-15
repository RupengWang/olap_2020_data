select os, os_version, cast(DS as varchar) as ds, count(distinct DISTINCT_ID2) as uv, count(DISTINCT_ID2) as pv from OLAP_2020_EVENT_TEST where DS <= '20200707' and DS >= '20200701' group by ds, os, os_version
union
select 'Android', '小计', cast(DS as varchar) as ds, count(distinct DISTINCT_ID2) as uv, count(DISTINCT_ID2) as pv from OLAP_2020_EVENT_TEST where DS <= '20200707' and DS >= '20200701' and os = 'Android' group by ds
union
select 'iOS', '小计', cast(DS as varchar) as ds, count(distinct DISTINCT_ID2) as uv, count(DISTINCT_ID2) as pv from OLAP_2020_EVENT_TEST where DS <= '20200707' and DS >= '20200701' and os = 'iOS' group by ds
union
select 'Android', '合计', '', count(distinct DISTINCT_ID2) as uv, count(DISTINCT_ID2) as pv from OLAP_2020_EVENT_TEST where DS <= '20200707' and DS >= '20200701' and os = 'Android'
union
select 'iOS', '合计', '', count(distinct DISTINCT_ID2) as uv, count(DISTINCT_ID2) as pv from OLAP_2020_EVENT_TEST where DS <= '20200707' and DS >= '20200701' and os = 'iOS'
union
select '总计', '', '', count(distinct DISTINCT_ID2) as uv, count(DISTINCT_ID2) as pv from OLAP_2020_EVENT_TEST where DS <= '20200707' and DS >= '20200701'
order by os, os_version, ds