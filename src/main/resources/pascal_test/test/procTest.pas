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
  {if x < y then
    m:= x
  else
    m:= y;
  if z < m then
    m:= z;}
  m:=1;
end; { end of procedure findMin }
begin
  {writeln(' Enter three numbers: ');
  readln( a, b, c);}
  findMin(a, b, 10>0, 1); (* Procedure call *)
//  findMin(1, b, c, max); (* Procedure call *)
  {writeln(' Minimum: ', min);}
end.