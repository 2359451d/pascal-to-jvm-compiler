program rocket(input,output);
var hours, minutes, seconds, flighttime: 0..MaxInt;
begin
  (* READ in and print out data *)
  read(hours, minutes, seconds);
  WriteLn('take off time is', hours:2,'/',minutes:2, '/', seconds:2);
  WriteLn;
  Read(flighttime);
  WriteLn('flight-time=', flighttime:7, 'seconds');
  writeln;

  (* Calculate time of arrival*)
  seconds:=seconds+flighttime;
  minutes:=minutes+seconds div 60;
  seconds:=seconds mod 60;
  hours:=hours + minutes div 60;
  minutes:=minutes mod 60;
  hours:=hours mod 24;

  writeln('expected time of arrival', hours:2, '/', minutes:2, '/', seconds:2);

end.