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
end; { end of procedure findMin }

procedure test;
begin
  a :=1;
end;

begin
  writeln(' Enter three numbers: ');
  readln( a, b, c);
  findMin(a,b,c, min);

  writeln(' Minimum: ', min);
end.
