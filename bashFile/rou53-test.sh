#!/bin/bash
echo "Running in LATENCY domain"
for i in {1..10}
do
sudo /etc/init.d/nscd restart
echo "LATENCY NORMAL: " $i
curl --no-sessionid -s -w '\nLookup time:\t%{time_namelookup}\nConnect time:\t%{time_connect}\nAppCon time:\t%{time_appconnect}\nRedirect time:\t%{time_redirect}\nPreXfer time:\t%{time_pretransfer}\nStartXfer time:\t%{time_starttransfer}\n\nTotal time:\t%{time_total}\n' -o /dev/null http://takipi-route53-testing-latency.com/takipi-aws/speed/normal
done

for i in {1..10}
do
sudo /etc/init.d/nscd restart
echo "LATENCY WAIT 100ms: " $i
curl --no-sessionid -s -w '\nLookup time:\t%{time_namelookup}\nConnect time:\t%{time_connect}\nAppCon time:\t%{time_appconnect}\nRedirect time:\t%{time_redirect}\nPreXfer time:\t%{time_pretransfer}\nStartXfer time:\t%{time_starttransfer}\n\nTotal time:\t%{time_total}\n' -o /dev/null http://takipi-route53-testing-latency.com/takipi-aws/speed/wait100
done

for i in {1..10}
do
sudo /etc/init.d/nscd restart
echo "LATENCY DOWNLOAD 1Mb: " $i
curl --no-sessionid -s -w '\nLookup time:\t%{time_namelookup}\nConnect time:\t%{time_connect}\nAppCon time:\t%{time_appconnect}\nRedirect time:\t%{time_redirect}\nPreXfer time:\t%{time_pretransfer}\nStartXfer time:\t%{time_starttransfer}\n\nTotal time:\t%{time_total}\n' -o /dev/null http://takipi-route53-testing-latency.com/takipi-aws/speed/res1mb
done

echo "End Running in LATENCY domain"
echo "======================================================="
echo "Running in GEO domain"

for i in {1..10}
do
sudo /etc/init.d/nscd restart
echo "GEO NORMAL: " $i
curl --no-sessionid -s -w '\nLookup time:\t%{time_namelookup}\nConnect time:\t%{time_connect}\nAppCon time:\t%{time_appconnect}\nRedirect time:\t%{time_redirect}\nPreXfer time:\t%{time_pretransfer}\nStartXfer time:\t%{time_starttransfer}\n\nTotal time:\t%{time_total}\n' -o /dev/null http://takipi-route53-testing-geo.com/takipi-aws/speed/normal
done

for i in {1..10}
do
sudo /etc/init.d/nscd restart
echo "GEO WAIT 100ms: " $i
curl --no-sessionid -s -w '\nLookup time:\t%{time_namelookup}\nConnect time:\t%{time_connect}\nAppCon time:\t%{time_appconnect}\nRedirect time:\t%{time_redirect}\nPreXfer time:\t%{time_pretransfer}\nStartXfer time:\t%{time_starttransfer}\n\nTotal time:\t%{time_total}\n' -o /dev/null http://takipi-route53-testing-geo.com/takipi-aws/speed/wait100
done

for i in {1..10}
do
sudo /etc/init.d/nscd restart
echo "GEO DOWNLOAD 1Mb: " $i
curl --no-sessionid -s -w '\nLookup time:\t%{time_namelookup}\nConnect time:\t%{time_connect}\nAppCon time:\t%{time_appconnect}\nRedirect time:\t%{time_redirect}\nPreXfer time:\t%{time_pretransfer}\nStartXfer time:\t%{time_starttransfer}\n\nTotal time:\t%{time_total}\n' -o /dev/null http://takipi-route53-testing-geo.com/takipi-aws/speed/res1mb
done

echo "End Running in GEO domain"


