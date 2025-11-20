s1.awk 
BEGIN { 
count = 0; 
} 
{ 
} 
event = $1; 
if (event == "d") { 
count++; 
} 
END { 
printf("\nNumber of packets dropped is: %d\n", count); 
} 
s1.tcl 
# create new simulation instance 
set ns [new Simulator] 
# open trace file 
set tracefile [open s1.tr w] 
$ns trace-all $tracefile 
# open nam: animation file 
set namfile [open s1.nam w] 
$ns namtrace-all $namfile 
# define finish procedure to perform at the end of simulation 
proc finish {} { 
global ns tracefile namfile 
$ns flush-trace 
# dump all traces and close files 
close $tracefile 
close $namfile 
# execute nam animation file 
exec nam s1.nam & 
# execute awk file in background 
exec awk -f s1.awk s1.tr & 
exit 0 
} 
# create 3 nodes 
set n0 [$ns node] 
set n1 [$ns node] 
set n2 [$ns node] 
# create labels 
$n0 label "TCP Source" 
$n2 label "TCP Sink" 
# set color 
$ns color 1 red 
# create link between nodes / create topology 
$ns duplex-link $n0 $n1 1Mb 10ms DropTail 
$ns duplex-link $n1 $n2 1Mb 10ms DropTail 
# set queue size of N packets between n1 and n2 
$ns queue-limit $ 
# create TCP agent and attach to node 0 
set tcp [new Agent/TCP] 
$ns attach-agent $n0 $tcp 
# create TCP sink agent and attach to node 2 
set tcpsink [new Agent/TCPSink] 
$ns attach-agent $n2 $tcpsink 
# create traffic: FTP: create FTP source agent on top of TCP and attach to TCP agent 
set ftp [new Application/FTP] 
$ftp attach-agent $tcp 
# connect TCP agent with TCP sink agent 
$ns connect $tcp $tcpsink 
# set the color 
$tcp set class_ 1 
# schedule events 
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
