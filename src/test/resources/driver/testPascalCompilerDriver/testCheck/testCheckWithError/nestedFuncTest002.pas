(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 14/01/2022
 *)
{$mode iso}
{$RANGECHECKS on}
program nestedFuncTest002;
var
  result :Integer;
  subrangeVar1: 1..10;
function sum:Integer;
  var
    left: Integer;
    right: Integer;
  function add(left, right: Integer):Integer;
    function addB:integer;
      begin
        addB:=1;
      end;
    begin
      add:= left+right;
      {add:= MaxInt+MaxInt+left+1;} {Checked at the Runtime}
    end;
  begin
    left:=1;
    right:=2;
    sum:=add(left,right);
    sum:=addB(left,right); {addB(left,right) is not defined}
  end;

begin
  result := sum;
  subrangeVar1 := 10;
end.
