(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 14/01/2022
 *)
{$mode iso}
{$RANGECHECKS on}
program nestedFuncTest;
var
  result :Integer;
  subrangeVar1: 1..10;
  subrangeVar2: 1..10;
function sum:Integer;
  var
    left: Integer;
    right: Integer;
    sumVar: Char;
  function add(left, right: Integer):Integer;
    begin
      add:= left+right;
      {add:= MaxInt+MaxInt+left+1;} {Checked at the Runtime}
    end;
  begin
    left:=1;
    right:=2;
    sum:=add(left,right);
  end;

begin
  result := sum;
  {WriteLn(result);}
  subrangeVar1 := 10;
end.