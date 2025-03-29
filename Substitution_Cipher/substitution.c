#include<stdio.h>
#include<string.h>
#include<ctype.h>
int main()
{
	char ea[100],eb[100],da[100],db[100];
	int n,i,option,key;
	printf("Enter Option(1 or 2):\t1.Encrypt\t2.Decrypt :\n");
	scanf("%d",&option);
	getchar();
	switch(option)
	{
		case 1:
		printf("Enter The Message to Encrypt(without spaces) :");	
		scanf("%s",ea);
		printf("Enter key (1 to 25) :");
		scanf("%d",&key);
		while (getchar() != '\n');// Consume any remaining newline characters
		if (key < 1 || key > 25) 
		{
        	printf("Invalid key. It should be between 1 and 25.\n");
            return 1;
        }

		n=strlen(ea);
		for (i = 0; i < n; i++) 
		{
		    ea[i] = toupper(ea[i]);
		}
		
		for (i = 0; i < n; i++) 
		{
			eb[i] = ((ea[i] - 'A' + key) % 26 + 26) % 26 + 'A';  
		}
		
		printf("Cipher Text :");
		for (i = 0; i < n; i++) 
		{
			printf("%c",eb[i]); 
		}
		break;
		printf("\n");
		
		case 2:
		printf("Enter Cipher Text (without spaces):");
		scanf("%s",da);
		printf("Enter key:");
		scanf("%d",&key);
		while (getchar() != '\n');// Consume any remaining newline characters
		if (key < 1 || key > 25) 
		{
        	printf("Invalid key. It should be between 1 and 25.\n");
            return 1;
        }
		n=strlen(da);
		
		for (i = 0; i < n; i++) 
		{
		    da[i] = toupper(da[i]);
		}
		
		for (i = 0; i < n; i++) 
		{
			db[i] = ((da[i] - 'A' - key + 26) % 26 + 26) % 26 + 'A';   
		}
		
		printf("Plain Text :");
		for (i = 0; i < n; i++) 
		{
			printf("%c",db[i]); 
		}
		break;
		
		default:
        printf("Invalid Option, Enter 1 or 2");
        break;
		
	}
	printf("\n");
	return 0;
}
