(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 2021/12/6
 *)
program whileTest;
var i,sum:integer;
  bool1: Boolean;
begin
  sum:=0;
  (* ↓ evaluation of the expression should be boolean
     Invalid, i is int, true is boolean
   *)
  while i=true do begin
    sum:=sum+i;
  end;

  while bool1=true do begin
    sum :=sum+1;
  end;
end.