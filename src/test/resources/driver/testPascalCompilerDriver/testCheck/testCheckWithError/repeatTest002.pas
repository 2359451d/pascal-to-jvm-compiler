(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 2021/12/6
 *)
program repeatTest;
var
  a: Integer;

begin
  a := 10;
  (* repeat until loop execution *)
  repeat
    a := a + 1;
  until a = 13.0;

  repeat
    a := a + 1;
  until a; {Not work, repeat expression must be boolean}
end.