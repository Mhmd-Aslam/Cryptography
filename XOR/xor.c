#include<stdio.h>
#include<string.h>
int main()
{
	char a[20] ="Hello_World";
	int n,i;
	n=strlen(a);
	for(i=0;i<n;i++)
	{
		printf("%c",a[i]^0);
	}
	printf("\n");
	return 0;
}
