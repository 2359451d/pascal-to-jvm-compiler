(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 14/01/2022
 *)
{$mode iso}
program nestedProcTest001pre;
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
    left:=1;
    right:=2;
  end;

begin
  sum;
  subrangeVar:=1;
  intVar:= 1 + subrangeVar;
  realVar:= intVar;
end.
