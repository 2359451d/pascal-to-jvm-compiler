(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 2021/12/6
 *)
program procTest003;
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
end; { end of procedure findMin }

procedure test;
begin
  a :=1;
end;

begin
  findMin(a,b,c, min); {Works}
  findMin:=1; {assigning value to a procedure is not allowed}
end.