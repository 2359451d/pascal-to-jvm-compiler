(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 2021/12/4
 *)
program arithOperator008;
var
  a,b,c : integer;
  d: real;
  e,f: Integer;
begin
  {some statements below are not valid}

  f := 10 + (10.0 + 1);{Not work, right is real, left is int}

end.