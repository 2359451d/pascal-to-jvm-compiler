(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 2021/12/6
 *)
{$mode iso}
{$RANGECHECKS on}
program procBlockTest001pre;
const
  n = 1000;
  intConst = 1;
var
  a, b, c,  min: integer;
procedure findMin(x, y, z: integer; var m: integer);
(* Finds the minimum of the 3 values *)
const
  n = 1; {n in proc-findmin scope}
  charconst = 'A';
  intConst = 'A';
type
  arr = array [1..n] of Integer;
var
  intVar:1..10;
  arrVar : arr;
begin
  arrVar[1]:= 100;

  if x < y then
    m:= x
  else
    m:= y;
  if z < m then
    m:= z;

end; { end of procedure findMin }
begin
  findmin(a, b, c, min); (* Procedure call *)
end.
