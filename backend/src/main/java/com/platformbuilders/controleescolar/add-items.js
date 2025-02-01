async function addSchools() {
    const schools = [
        {
            "name": "Colégio Monteiro Lobato",
            "cnpj": "36.963.885/0001-71",
            "address": "Avenida Central, 1234, Centro",
            "phone": "+55 11 98765-4321",
            "studentsPerClassroom": 25
        },
        {
            "name": "Escola Novo Saber",
            "cnpj": "15.883.817/0001-22",
            "address": "Rua das Acácias, 456, Vila Nova",
            "phone": "+55 21 99988-7766",
            "studentsPerClassroom": 30
        },
        {
            "name": "Instituto Futuro Brilhante",
            "cnpj": "78.093.737/0001-93",
            "address": "Praça da Independência, 12, Centro",
            "phone": "+55 31 99876-5432",
            "studentsPerClassroom": 28
        },
        {
            "name": "Centro Educacional Luz do Saber",
            "cnpj": "56.219.800/0001-52",
            "address": "Rua dos Girassóis, 99, Bairro das Flores",
            "phone": "+55 41 98754-3210",
            "studentsPerClassroom": 22
        },
        {
            "name": "Escola Conexão do Conhecimento",
            "cnpj": "73.832.782/0001-07",
            "address": "Avenida das Américas, 200, Jardim América",
            "phone": "+55 51 99632-1122",
            "studentsPerClassroom": 27
        },
        {
            "name": "Colégio Integração Global",
            "cnpj": "",
            "address": "Rua do Sol, 303, Bairro Novo",
            "phone": "+55 61 99777-8899",
            "studentsPerClassroom": 24
        },
        {
            "name": "Escola Crescer e Aprender",
            "cnpj": "64.439.188/0001-51",
            "address": "Rua das Árvores, 58, Vila Esperança",
            "phone": "+55 71 99555-2233",
            "studentsPerClassroom": 26
        },
        {
            "name": "Instituto Saber Mais",
            "cnpj": "77.560.482/0001-69",
            "address": "Alameda dos Ipês, 76, Parque das Águas",
            "phone": "+55 81 99444-3344",
            "studentsPerClassroom": 29
        },
        {
            "name": "Centro de Ensino Nova Geração",
            "cnpj": "53.333.481/0001-13",
            "address": "Rua Principal, 500, Centro Histórico",
            "phone": "+55 91 99333-4455",
            "studentsPerClassroom": 20
        },
        {
            "name": "Escola Horizonte do Saber",
            "cnpj": "53.486.845/0001-03",
            "address": "Travessa dos Estudantes, 10, Bairro Universitário",
            "phone": "+55 83 99222-5566",
            "studentsPerClassroom": 32
        }
    ];




    async function addItem(item) {
        const url = 'http://localhost:8080/api/schools';
        const options = {
            method: 'POST',
            headers: {
                'content-type': 'application/json'
            },
            body: JSON.stringify(item)
        };
        console.log(options.body)

        try {
            const response = await fetch(url, options);
            console.log(response)
            const data = await response.json();
            console.log(data);
        } catch (error) {
            console.error(error);
        }
    }

    for (const school of schools) {
        await addItem(school); // Aguarda a requisição antes de ir para o próximo item
    }
}


async function addStudents() {
    const students = [
        {
            "firstName": "Ana",
            "lastName": "Souza",
            "gender": "FEMALE",
            "age": 15,
            "level": "HIGH_SCHOOL",
            "guardianName": "Carlos Souza"
        },
        {
            "firstName": "Lucas",
            "lastName": "Oliveira",
            "gender": "MALE",
            "age": 16,
            "level": "HIGH_SCHOOL",
            "guardianName": "Paulo Oliveira"
        },
        {
            "firstName": "Beatriz",
            "lastName": "Costa",
            "gender": "FEMALE",
            "age": 14,
            "level": "HIGH_SCHOOL",
            "guardianName": "Marta Costa"
        },
        {
            "firstName": "Gabriel",
            "lastName": "Pereira",
            "gender": "MALE",
            "age": 15,
            "level": "HIGH_SCHOOL",
            "guardianName": "Rita Pereira"
        },
        {
            "firstName": "Mariana",
            "lastName": "Silva",
            "gender": "FEMALE",
            "age": 17,
            "level": "HIGH_SCHOOL",
            "guardianName": "José Silva"
        },
        {
            "firstName": "Felipe",
            "lastName": "Almeida",
            "gender": "MALE",
            "age": 16,
            "level": "HIGH_SCHOOL",
            "guardianName": "Carlos Almeida"
        },
        {
            "firstName": "Juliana",
            "lastName": "Fernandes",
            "gender": "FEMALE",
            "age": 15,
            "level": "HIGH_SCHOOL",
            "guardianName": "Roberta Fernandes"
        },
        {
            "firstName": "João",
            "lastName": "Martins",
            "gender": "MALE",
            "age": 17,
            "level": "HIGH_SCHOOL",
            "guardianName": "Luiza Martins"
        },
        {
            "firstName": "Camila",
            "lastName": "Santos",
            "gender": "FEMALE",
            "age": 16,
            "level": "HIGH_SCHOOL",
            "guardianName": "Marcelo Santos"
        },
        {
            "firstName": "Ricardo",
            "lastName": "Melo",
            "gender": "MALE",
            "age": 15,
            "level": "HIGH_SCHOOL",
            "guardianName": "Valéria Melo"
        },
        {
            "firstName": "Larissa",
            "lastName": "Rocha",
            "gender": "FEMALE",
            "age": 14,
            "level": "HIGH_SCHOOL",
            "guardianName": "Fernando Rocha"
        },
        {
            "firstName": "Eduardo",
            "lastName": "Gomes",
            "gender": "MALE",
            "age": 17,
            "level": "HIGH_SCHOOL",
            "guardianName": "Renata Gomes"
        },
        {
            "firstName": "Paula",
            "lastName": "Martins",
            "gender": "FEMALE",
            "age": 15,
            "level": "HIGH_SCHOOL",
            "guardianName": "Gustavo Martins"
        },
        {
            "firstName": "Rafael",
            "lastName": "Lima",
            "gender": "MALE",
            "age": 16,
            "level": "HIGH_SCHOOL",
            "guardianName": "André Lima"
        },
        {
            "firstName": "Tatiane",
            "lastName": "Barros",
            "gender": "FEMALE",
            "age": 14,
            "level": "HIGH_SCHOOL",
            "guardianName": "Marcela Barros"
        },
        {
            "firstName": "Vinícius",
            "lastName": "Oliveira",
            "gender": "MALE",
            "age": 15,
            "level": "HIGH_SCHOOL",
            "guardianName": "Cláudio Oliveira"
        },
        {
            "firstName": "Fernanda",
            "lastName": "Rodrigues",
            "gender": "FEMALE",
            "age": 16,
            "level": "HIGH_SCHOOL",
            "guardianName": "Diana Rodrigues"
        },
        {
            "firstName": "Matheus",
            "lastName": "Carvalho",
            "gender": "MALE",
            "age": 15,
            "level": "HIGH_SCHOOL",
            "guardianName": "Simone Carvalho"
        },
        {
            "firstName": "Isabela",
            "lastName": "Azevedo",
            "gender": "FEMALE",
            "age": 17,
            "level": "HIGH_SCHOOL",
            "guardianName": "Ricardo Azevedo"
        },
        {
            "firstName": "Carlos",
            "lastName": "Dias",
            "gender": "MALE",
            "age": 14,
            "level": "HIGH_SCHOOL",
            "guardianName": "Eliane Dias"
        }
    ]
        ;




    async function addItem(item) {
        const url = 'http://localhost:8080/api/students';
        const options = {
            method: 'POST',
            headers: {
                'content-type': 'application/json'
            },
            body: JSON.stringify(item)
        };
        console.log(options.body)

        try {
            const response = await fetch(url, options);
            console.log(response)
            const data = await response.json();
            console.log(data);
        } catch (error) {
            console.error(error);
        }
    }

    let i = 0;
    for (const student of students) {
        i++;
        await addItem({
            ...student,
            schoolId: i % 2 ? '1' : '2'
        }); // Aguarda a requisição antes de ir para o próximo item
    }
}


async function main() {
    await addSchools();
    await addStudents();
}

main();