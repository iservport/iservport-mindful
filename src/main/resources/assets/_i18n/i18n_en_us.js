angular.module('app.services').
value('common', {
	_ACTIVE:'Ativo',
	_ADD:'Adicionar',
	_ACCEPTED_TERMS_OF_USE :'Li e aceito os termos de uso.',
	_ATTENTION:'Atenção',
	_CLOSE:'Fechar',
	_CODE:'Código',
	_CITY: 'Cidades',
	_CHECK_EMAIL: 'Verifique seu email para recuperar sua senha.',
	_CHECK_EMAIL_FAILED: 'Não foi possível enviar o e-mail de verificação, por favor, tente novamente mais tarde.',
	_CONTINUE: 'Continue',
    _CONFIRMATION: 'Confirmação',

	_DELETE:'Excluir',
	_DETAILS:'Detalhes',
	_DESC:'Descrição',
	_DENIED: 'Negado!',
	_EDIT:'Editar',
	_EDIT_FOLDER:'Editar pasta',
	_EXIT:'Sair',
	_EMAIL_ALREADY_REGISTERED: 'Este e-mail já está cadastrado, solicite mais informações ao seu administrador.',
	_ENTER: 'Enter',
	_ERROR_403 : 'Ocorreu um erro, provavelmente sua sessão expirou ou seu acesso foi negado, tente novamente!',
	_EXPORT : 'Exportar',
	_INACTIVE:'Inativo',
	_INCLUDE:'Incluir',
	_ISSUED:'Emissão',
	_INVALID_EMAIL: 'Email Inválido.',
    _INACTIVE_USER: 'Usuário inativo.',

	_HIDE_INACTIVES:'Ocultar inativos',

	_MAX:'Máx.',
	_MAX_CHAR:'Número máximo de caracteres',
	_MORE:'Mais',
	_NAME:'Nome',
	_NEW:'Novo',
	_NEW_FOLDER:'Nova pasta',
	_NEXT_CHECK:'Verificar até',
	_NR : 'Número' ,
	_OR:'ou',

	_PEOPLE:'Pessoas',
    _PRIVACY_POLICY:'Política de privacidade',
    _PRINT:'Imprimir',

	_REQUEST:'Ocorrências',
	_RESOLUTION:'Situação',
	_RESOLUTION_I:'Indefinido',
	_RESOLUTION_P:'Planejado',
	_RESOLUTION_T:'Monitorado',
	_RESOLUTION_D:'Encerrado',
	_RESOLUTION_X:'Cancelado',
	_SAVE:'Salvar',
	_SELECT:'Selecionar',
	_SEARCH:'Pesquisar',
	_SEARCH_USER:'Pesquisar usuários',
	_SHOW_INACTIVES:'Mostrar inativos',
    _STATE : 'Estados',

    _TERMS_OF_USE:'Termos de uso',

    _LOGIN_ERROR: 'Erro no login.',
    _LOGIN_CREATE:'Crie uma conta',
    _LOGIN_FORGOT:'Esqueceu sua senha?',
    _LOGIN_JOIN:'Ainda não possui acesso?',
    _LOGIN_REMEMBER:'Lembrar senha',

    _SEARCH_ENTITY : 'Pesquisar empresa',
    _SIGNUP_CONTACT:'Dados para contato',
    _SIGNUP_FREE_TITLE:'Experimente gratuitamente' ,
    _SIGNUP_FREE:'O registro é grátis para startups e microempresas.' ,
    _SIGNUP_FREE_DETAIL:'Nada será cobrado enquanto você não ultrpassar o limite de número de usuários estabelecido para estas empresas.',
    _SIGNUP_FREE_TEXT:'Caso você inicie e deseje, agora ou mais tarde, utilizar todo o potencial do iservport, nós não realizaremos nenhuma cobrança sem o expresso consentimento do administrador da sua conta (você ou a pessoa autorizada na sua empresa).',
    _SIGNUP_REQUEST:'Se sua empresa já possui registro, ',
    _SIGNUP_REQUEST_LINK:'solicite sua senha de acesso pessoal ao administrador',
    _SEE_TERMS_OF_USE: 'See terms of use.',
    _SEND_EMAIL: 'Enviar confirmação e email.',

    _OR:'Ou',

    _E_MAIL_SENT:'Um e-mail de confirmação foi enviado para o endereço fornecido.',
    _E_MAIL_SPAM_WARN:'Caso não tenha recebido, por favor, verifique sua caixa de spam.',
    _ALREADY_HAVE_ACCOUNT:'Já possui acesso?',
    _BACK_TO_LOGIN:'Voltar ao login',

	CERT_TITLE: 'Certificado de Treinamento',
	CERT_NUMBER: 'Certificado nr.:',
	CERT_STATEMENT: 'O presente documento certifica que',
	CERT_COURSE_NAME: 'Participou do curso de',
	CERT_COURSE_LOCAL: 'Local',
	CERT_COURSE_START: 'Início',
	CERT_COURSE_END: 'Término',
	CERT_COURSE_DURATION: 'Duração',
	CERT_COURSE_HOURS: 'horas',
	CERT_COURSE_TAUGHT_BY: 'Ministrado por',
	CERT_COURSE_APPROVED_BY: 'Aprovado eletronicamente por',
	CERT_COURSE_INSTRUCTOR: 'Instrutor',

	DISPLAY_NAME:'Mostrar como',
    DOMAIN: 'Dominio',

	FIRST_NAME:'Primeiro nome',


	IDENTITY_GENDER_FEMALE:'Feminino',
	IDENTITY_GENDER_MALE:'Masculino',
	IDENTITY_GENDER_NOT_SUPPLIED:'Não informado',
	IDENTITY_GENDER:'Gênero',
	IDENTITY_TYPE_NOT_ADDRESSABLE:'Somente para identificação',
	IDENTITY_TYPE_ORGANIZATIONAL_EMAIL:'Fornecido pela organização',
	IDENTITY_TYPE_PERSONAL_EMAIL:'Pessoal',
	IDENTITY_TYPE:'O e-mail é',

	LAST_NAME:'Sobrenome',

	PERSONAL_DATA: 'Dados pessoais',
	PASSWORD_TO_CHANGE: 'Senha',
	PASSWORD_CONFIRMATION: 'Confirmação de senha',
    FAILED_TO_CHANGE_PASSWORD: 'Falha ao mudar senha.',
    OK_TO_CHANGE_PASSWORD: 'Senha modificada com sucesso.',
    UPDATE_PASSWORD: 'Atualizar senha',
    PASSWORD: 'Senha',
    PASSWORD_CREATE: 'Criar senha.',
    RECOVERY_PASSWORD: 'Recuperar Senha',
    CHANGE_PASSWORD: 'Mudar senha',


	USER_BIRTH_DATE:'Nascimento',
    USER_ACTIVE: 'Seu usuário já está ativo, faça seu login.',

    VERIFY_EMAIL: 'Verifique seu email',

    SEND_EMAIL_TO_RECOVER: 'Enviar email para recuperar senha.',

        TOOLTIP_RESOLUTION_P:'Aguardando',
        TOOLTIP_RESOLUTION_T:'Monitorando',
        TOOLTIP_RESOLUTION_D:'Concluído',
        TOOLTIP_RESOLUTION_X:'Cancelado',

	_getLocalizationKeys: function() {
//		Returns an object that has as properties the same properties of this object.
//		The values of these properties is equal to the name of each properties.
		var keys = {};
		for (var k in this) {
			keys[k] = k;
		}
		return keys;
	}
});