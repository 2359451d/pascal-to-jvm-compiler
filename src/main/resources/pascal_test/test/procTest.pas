(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 2021/12/6
 *)
program procTest;
var
  a, b, c,  min: integer;
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
  findMin := 1;
end; { end of procedure findMin }
begin
  {writeln(' Enter three numbers: ');
  readln( a, b, c);}
  (*findMin(a,b,c, min); {Works} *)
  findMin(true, b, 10>0, 1); (*Not Work Procedure call *)
  findMin(1, b, c, max); (* Not work Proceure call *)
  {writeln(' Minimum: ', min);}
end.