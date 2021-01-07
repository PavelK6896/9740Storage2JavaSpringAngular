export interface Client {
    id?: string
    phone: string
    name: string
    title: string
    // date: Date
}


export interface LoginRequestDto {
    username: string;
    password: string;
}


export interface LoginResponseDto {
    authenticationToken: string;
    // refreshToken: string;
    expiresAt: Date;
    username: string;
}

export interface InfoMessage {
    flag: boolean;
    message: string;
}
