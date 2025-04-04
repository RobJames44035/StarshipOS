#include <efi.h>
#include <efilib.h>

EFI_STATUS EFIAPI efi_main(EFI_HANDLE image_handle, EFI_SYSTEM_TABLE *system_table) {
    InitializeLib(image_handle, system_table);
    system_table->ConOut->OutputString(system_table->ConOut, L"Hello, StarshipOS!\r\n");
    return EFI_SUCCESS;
}
