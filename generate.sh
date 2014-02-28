#!/bin/sh

# Generate Project Express for Linux
VERSION="0.1"

# inicio da execucao
START=`date`
START_S=$(date +%s)

ROOT_DIR=`pwd`

# arquivo de log
GENERATE_LOG="${ROOT_DIR}/generate_log.txt"

Main() {
	echo ""
	echo "Generate Project Express for Linux ${VERSION} [${PROJECT}]"
	echo ""
	echo "Bom dia ${USER}!"
	echo "Para consultar o resultado da geração verifique o arquivo ${GENERATE_LOG}."
	echo "Para cancelar o script use \"Ctrl+C\"."
	echo "---------------------------------------------------------------------"

	if [ $# -eq 0 ]; then
		Help
		exit 1
	fi

	case $1 in
		"-g")
			shift ;
			CheckProjectsList $* ;
			DoTask generate $* ;;
		"--clean")
			shift ;
			echo "" ;
			echo "Limpando projeto..." ;
			#TASK: Colocar para remover os projetos
			#maven clean >> "${MAVEX_LOG}" ;
			;;
		*)
			Help ;
			exit 1 ;;
	esac
}

CheckProjectsList() {
	for module in $*; do
		case $module in
			"sistemaacademico") ;;
			"mprural") ;;
			*)
				Help $module;
				exit 1;;
		esac
	done
}

DoTask() {
	task=$1
	shift

	case $task in
		"generate")
			task=0 ;;
		"mprural")
			task=0 ;;
		*) ;;
	esac

	while [ $# -gt 0 ]; do
		echo ""
		if [ $task -eq 0 ]; then
			echo "Gerando o Projeto: $1"
		fi
		echo "Aguarde..."

		case $1 in
			"sistemaacademico")
				GenerateSistemaAcademico $task sistemaacademico
				;;
			"mprural")
				GenerateMPRual $task mprural
				;;
			*) ;;
		esac

		shift
	done
}

GenerateMPRual() {
	target=$1
	task=$2
	module=$3

	echo ""
	echo ""

	rm -R mprural;
	./gen-mprural.sh;
	cp models/MPRural.xml mprural/mda/src/uml/;
	cd mprural;
	sed -i 's/cartridge.version=3.1.1.3.4.19-RC5/cartridge.version=3.1/g' build.properties;
	cp -R ../models/mprural ../;
	maven install deploy;
}

GenerateSistemaAcademico() {
	target=$1
	task=$2
	module=$3

	echo ""
	echo ""

	rm -R sistemaacademico;
	./gen-sistemaacademico.sh;
	cp models/SistemaAcademico.xml sistemaacademico/mda/src/uml/;
	cd sistemaacademico;
	sed -i 's/cartridge.version=3.1.1.3.4.19-RC3/cartridge.version=3.1/g' build.properties;
	maven;
	maven mda -Dprojeto=sistemaacademico-geral-Estudante;
	maven mda -Dprojeto=sistemaacademico-geral-Disciplina;
	maven install deploy;

#	cd "${ROOT_DIR}/core/cs/${module}"
#	if [ $task -eq 0 ]; then
#		maven ejb:install deploy >> "${MAVEX_LOG}"
#	elif [ $task -eq 1 ]; then
#		maven deploy >> "${MAVEX_LOG}"
#	elif [ $task -eq 2 ]; then
#		maven undeploy >> "${MAVEX_LOG}"
#	fi
#	echo "------ Resultado do maven para ${target} (./core/cs/${module}): $?"
#	CheckError ${target} "./core/cs/${module}" $?
}

Help() {
	target=$1

	if [ "$target" != "" ];then
		echo ""
		echo "Objetivo inexistente: ${target}"
		echo ""
	fi

	echo "Uso: ${0} [opcao] [lista de objetivos]"
	echo ""
	echo "[opcao]"
	echo "\t -a [--all] \t Gera e compila o codigo de todos os modulos."
}

DisplayTime() {
	# termino da execucao
	END=`date`
	END_S=$(date +%s)
	# tempo de execucao
	DIFF=$(( $END_S - $START_S ))

	min=`expr $DIFF / 60`
	sec=`expr $DIFF - $min \* 60`

	if [ $min -lt 10 ]; then
		min="0${min}"
	fi
	if [ $sec -lt 10 ]; then
		sec="0${sec}"
	fi

	echo ""
	echo "Tempo de execucao: ${min}:${sec}"
	echo "Comecou em: $START"
	echo "Terminou em: $END"
	echo ""
}

# gerando um novo arquivo de log
cat /dev/null > "${GENERATE_LOG}"

cd "${ROOT_DIR}"

Main $*

DisplayTime

exit 0

