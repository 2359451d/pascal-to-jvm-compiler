(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 14/02/2022
 *)
program logicalOpTest(output);
var
bool1: Boolean;
int1: Integer;
begin
  bool1 := false and false;
  bool1 := true and false;
  bool1 := true or false;
  WriteLn(bool1); {true}
  WriteLn(false and false); {false}
  WriteLn(true and(false or true)); {true}

  bool1 := not true;
  WriteLn(bool1); {false}

  WriteLn(not (false and true)); {true}

end.