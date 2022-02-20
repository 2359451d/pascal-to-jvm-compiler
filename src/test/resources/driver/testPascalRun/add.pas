program add(input, output);
{const
  haha = 'haha';
type
  SUMMER = (April, May, June, July, September);}

var first, second, sum: integer;{global}
char1, char2, char3:Char;
bool1, bool2:Boolean;
real1, real2:Real;

begin
  Read(first, second); {must convert to 2 read statements}
  sum := first+second+first;
  writeln('sum is: ', sum);
  WriteLn('11+sum is', 11+sum);
  Writeln(1,2,3,4, 5);
  writeln(1+2+3+4);
  Writeln(11-22); {-11}
  WriteLn(300-1);
  WriteLn(MaxInt-1);{2147483646}
  write((1-2-0)*(3+4)); {-7}

  first:=1;
  WriteLn(sum-first); {2147483646}

  sum:= MaxInt; {2147483647}
  WriteLn(sum*2); {overflow but JVM wont complain about it}

  WriteLn(1.0+2);{3.000000000E+00}
  WriteLn(2+1.0); {3.0}
  WriteLn();

  WriteLn(1.0+2*2); {5.0}
  WriteLn((1+1)* (2.0+3)); {10.0}
  {real1 := (1+1) * (2.0+3);
  WriteLn(real1);}
  real1 := (1-1-1) * (2.0+3);
  WriteLn(real1);{-5.0}
  WriteLn(2-1.0);
  WriteLn(2.0-1);
  WriteLn(2.0-0.1);{1.9}

  (*Ord(April);*)
  {write(1+2);
  write(0E0);
  Writeln(1,2,3,4, 5);
  Write('a', 1, 'b');}
end.