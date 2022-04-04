(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 2021/12/6
 *)
program procTest001;
var
  a, b, c,  min: integer;
{findMin:Integer;}
procedure findMin(x, y, z: integer; var m: integer);
(* Finds the minimum of the 3 values *)
begin
  if x < y then
    m:= x
  else
    m:= y;
  if z < m then
    m:= z;
  m:=1;
  findMin := 1; {Not work, assign value to proc}
end; { end of procedure findMin }

begin
end.
