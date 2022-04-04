(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 16/01/2022
 *)
program nonArgsSubprogramTest(input, output);

{forward}
function return2:Integer; forward;

procedure what;
begin
  WriteLn('what');
end;

function return1:Integer;
begin
  return1:=1;
end;

function return2;
begin
  return2:=2;
end;

var
  i : Integer;

begin
  what;
  what();

  { semantic errors
  return1;
  return1();
  }

  i := return1;
  i := return1();
  WriteLn(i);
  WriteLn(return1);
  WriteLn(return1());
  WriteLn('return1 ', return1);

  i:= return2();
  i:= return2;
  WriteLn('return2 ',return2);
  WriteLn('return2 ',return2());
end.