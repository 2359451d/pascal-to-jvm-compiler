program division(input, output);
var x,y,quotient,remainder:0..MaxInt;
begin
  %
  h
  read(x,y);
  remainder:=x; quotient:=0;
  while remainder>=y do
  begin
    quotient:=quotient+1;
    remainder:=remainder-y
  end;
  writeln(x,' divided by',y,'equals', quotient, ',remainder',remainder)
end.