/*
 *	MSS Code Factory CFLib DbUtil
 *
 *	Copyright (c) 2025 Mark Stephen Sobkow
 *
 *	This file is part of MSS Code Factory 3.0.
 *
 *	MSS Code Factory 3.0 is free software: you can redistribute it and/or modify
 *	it under the terms of the Apache v2.0 License as published by the Apache Foundation.
 *
 *	MSS Code Factory 3.0 is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 *	You should have received a copy of the Apache v2.0 License along with
 *	MSS Code Factory.  If not, see https://www.apache.org/licenses/LICENSE-2.0
 *
 *	Contact Mark Stephen Sobkow at mark.sobkow@gmail.com for commercial licensing or
 *  customization.
 */

//package server.markhome.msscf.msscf.cflib.dbutil;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import server.markhome.msscf.msscf.cflib.dbutil.CFLibDbKeyHash256;

@Converter(autoApply = true)
public class CFLibDbKeyHash256Converter implements AttributeConverter<CFLibDbKeyHash256, byte[]> {
    
    @Override
    public byte[] convertToDatabaseColumn(CFLibDbKeyHash256 attribute) {
        return attribute != null ? attribute.getBytes() : null;
    }

    @Override
    public CFLibDbKeyHash256 convertToEntityAttribute(byte[] dbData) {
        return dbData != null ? new CFLibDbKeyHash256(dbData) : null;
    }
}
