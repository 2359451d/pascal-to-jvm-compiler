(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 14/01/2022
 *)
{$mode iso}
program nestedProcTest005;
var
  intVar:Integer;
  realVar:Real;
  subrangeVar:1..10;

procedure sum;
var
  left: Integer;
  right: Integer;
  sumVar: Integer;
function add(left, right: Integer):Integer;
var
  flag: Boolean;
begin
  flag:= left<right;
  add:= 1;
end;
begin
  WriteLn(add(1,2));
  WriteLn(sum); {proc has no return value}
end;

begin
  sum;
  subrangeVar:=1;
  intVar:= 1 + subrangeVar;
  realVar:= intVar;
end.
