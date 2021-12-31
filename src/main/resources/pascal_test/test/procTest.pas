(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 2021/12/6
 *)
program procTest;
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

procedure test;
begin
  a :=1;
end;

begin
  {writeln(' Enter three numbers: ');
  readln( a, b, c);}
  (*findMin(a,b,c, min); {Works} *)

  findMin:=1; {assigning value to a procedure is not allowed}

  findMin(1,2,3); {if findMin redeclared as integer, then this is a invalid proc statemnt }

  a := findMin(a,b,c,min);
  a := test;
  {findMin(true, b, 10>0, 1); (*Not Work Procedure call *)
  findMin(1, b, c, max); (* Not work Proceure call *)}
  {writeln(' Minimum: ', min);}
end.