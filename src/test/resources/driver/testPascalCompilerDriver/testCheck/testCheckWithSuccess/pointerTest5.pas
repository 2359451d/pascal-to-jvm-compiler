program exPointertoFunctions;
type
  iptr = ^integer;

var
  i: integer;
  ptr: iptr;

function getNumber(p: iptr): integer;
var
  num: integer;

begin
  num:=100;
  p:= @num;
  getNumber:=p^;
end;

begin
  i := getNumber(ptr);

  writeln(' Here the pointer brings the value ', i);
end.