(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 14/01/2022
 *)
{$mode iso}
program nestedProcTest;
var
  intVar:Integer;
 realVar:Real;
  subrangeVar:1..10;

procedure sum;
  var
    left: Integer;
    right: Integer;
    sum: Integer; {duplicate identifier is not allowed}
    sumVar: Integer;
  function add(left, right: Integer):Integer;
    type
      right = Integer; {duplicate identifier is not allowed}
    var
      flag: Boolean;
    begin
      flag:= left<right;
      add:= 1.0; {Illegal assignment: [add:=1.0], expected: integer}
    end;
  begin
    left:=1;
    right:=2;
    sum:=add(left,right); {assigning value to a procedure is not allowed}
    {WriteLn(sum);}
  end;

begin
  sum;
  subrangeVar:=1;
  intVar:= 1 + subrangeVar;
  realVar:= intVar;
end.