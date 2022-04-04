{$mode iso}
{$RANGECHECKS ON}
program arrayTestB006;

var
  arrVar1, arrVar3: array [1..10] of Integer;
  arrVar2: array [1..100] of Integer;

begin
  arrVar1:=arrVar3;
  arrVar1:=arrVar2;{incompatible array types}

end.
