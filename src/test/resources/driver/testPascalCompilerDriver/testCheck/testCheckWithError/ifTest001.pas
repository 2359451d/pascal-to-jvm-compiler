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

  if i > 1 then
  begin
    i :=10;            i := i - 1;
  end;

  if i > true then {Not work, invalid condition type}
  begin
    i :=10;            i := i - 1;
  end;
end.