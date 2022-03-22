(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 23/01/2022
 *)
program withStateTest;
Type AR = record
    X,Y : Integer;
  end;

other = record
    id: Integer;
  end;

Var S : Ar;
T,O:other;

begin
  {S.X := 1;S.Y := 1;}
{  T.X := 2;T.Y := 2;}
  T.id:=100;
  With S,T do
    {WriteLn (X,' ',Y);}
  begin
    x:=100;
    y:=999;
  end;
  {WriteLn(S.x, S.Y);}

  with S do
    with T do
    begin
      x:=100;
      y:=999;
    end;
end.