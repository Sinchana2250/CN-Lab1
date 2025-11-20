s1.awk 
BEGIN { 
count = 0; 
} 
{ 
event = $1; 
if (event == "d") { 
count++; 
} 
END { 
printf("\nNumber of packets dropped is: %d\n", count); 
} 

  
s1.tcl 
set ns [new Simulator] 
set tracefile [open s1.tr w] 
$ns trace-all $tracefile 
set namfile [open s1.nam w] 
$ns namtrace-all $namfile 
proc finish {} { 
global ns tracefile namfile 
$ns flush-trace  
close $tracefile 
close $namfile 
exec nam s1.nam &  
exec awk -f s1.awk s1.tr & 
exit 0 
} 

  

set n0 [$ns node] 
set n1 [$ns node] 
set n2 [$ns node] 
  
$n0 label "TCP Source" 
$n2 label "TCP Sink" 

$ns color 1 red 
  

$ns duplex-link $n0 $n1 1Mb 10ms DropTail 
$ns duplex-link $n1 $n2 1Mb 10ms DropTail 
  
$ns queue-limit $ 
  

set tcp [new Agent/TCP] 
$ns attach-agent $n0 $tcp 

set tcpsink [new Agent/TCPSink] 
$ns attach-agent $n2 $tcpsink 

set ftp [new Application/FTP] 
$ftp attach-agent $tcp 

$ns connect $tcp $tcpsink 

$tcp set class_ 1 

$ns at 0.2 "$ftp start" 
$ns at 2.5 "$ftp stop" 
$ns at 3.0 "finish" 
$ns run

  0.25-0
  0.50-0
  0.75-0
  1.00-0
  1.25-9
  1.50-12
  1.75-15
  2.00-13
sudo ns scl.tcl
  xgraph -x "bandwidth" -y "numberofpackets dropped" 23cs158
