#include<stdio.h>
#include<string.h>
int main()
{
	char a[100] ="Computer";
	char b[100];
	int n,i;
	n=strlen(a);
	
	printf("XOR OPERATION : ");
	for(i=0;i<n;i++)
	{
		printf("%c",a[i]^127);
		b[i]=a[i]^127;
		
	}	
	
	printf("\n");
	printf("DXOR OPERATION : ");
	for(i=0;i<n;i++)
	{
		printf("%c",b[i]^127);
		
	
	}
	printf("\n");
	return 0;
}
