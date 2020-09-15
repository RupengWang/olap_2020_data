select lib, ds, count(distinct DISTINCT_ID2) as uv, count(DISTINCT_ID2) as pv, sum(price)
from OLAP_2020_EVENT_TEST join OLAP_2020_PROFILE_TEST
on OLAP_2020_EVENT_TEST.DISTINCT_ID = OLAP_2020_PROFILE_TEST.DISTINCT_ID
where DS <= '20200707' and DS >= '20200701' and xwhat='addtoshoppingcart'
group by lib, ds
order by lib, ds