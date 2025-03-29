#include<stdio.h>
#include<string.h>
int main()
{
	char str[1000];
	int n,sum=0;
	printf("Enter a String:");
	fgets(str, sizeof(str), stdin);
	n=strlen(str);
	for(int i=0;i<n;i++)
	{
		sum = (sum + str[i]);
		
	}
	printf("Hash Value = %x",sum);
	
	printf("\n");
	return 0;
}
