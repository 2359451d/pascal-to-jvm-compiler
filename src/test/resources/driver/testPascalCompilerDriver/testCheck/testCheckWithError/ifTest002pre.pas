(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 2021/12/5
 *)
program ifTest002pre;
var
  i,j:Integer;
begin
  i:= 11;

  {nested if}
  if j=0 then
    if i<1 then i:=1
    else i:=2
  else i:=3;

end.
