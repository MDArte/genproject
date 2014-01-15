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
	echo "Para consultar o resultado do maven verifique o arquivo ${GENERATE_LOG}."
	echo "Para cancelar o script use \"Ctrl+C\"."
	echo "---------------------------------------------------------------------"

	if [ $# -eq 0 ]; then
		Help
		exit 1
	fi

	case $1 in
		"-c")
			shift ;
			CheckModuleCompileList $* ;
			DoTask compile $* ;;
		"-d")
			shift ;
			CheckModuleCompileList $* ;
			DoTask deploy $* ;;
		"-m")
			shift ;
			CheckModuleModelList $* ;
			CodeGenerate $* ;;
		"-t")
			shift ;
			CheckModuleModelList $* ;
			All $* ;;
		"-u")
			shift ;
			CheckModuleCompileList $* ;
			DoTask undeploy $* ;;
		"-a" | "--all")
			shift ;
			All "core" "principal" "geral" "nucleo" "listaBrasil" "colecoes" "herbarioVirtual" ;;
		"--clean")
			shift ;
			echo "" ;
			echo "Limpando projeto..." ;
			maven clean >> "${MAVEX_LOG}" ;;
		*)
			Help ;
			exit 1 ;;
	esac
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

