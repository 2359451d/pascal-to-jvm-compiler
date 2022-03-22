(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 2021/12/5
 *)
program ifTest;
var
  i,j:Integer;
begin
  i:= 11;
  j:=0;

  if i < 10 then i := 10 else i:=1;
  if i - 10 >0 then i := i-5;

  {nested if}
  if j=0 then
    if i<>1 then i:=1
    else i:=2
  else i:=3;
end.